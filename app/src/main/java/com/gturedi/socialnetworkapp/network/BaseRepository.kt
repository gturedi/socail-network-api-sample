package com.gturedi.socialnetworkapp.network

import com.google.gson.Gson
import com.gturedi.socialnetworkapp.network.model.Resource
import com.gturedi.socialnetworkapp.network.model.SocialNetworkResponse
import okio.Timeout
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketException
import java.net.UnknownHostException

open class BaseRepository {

    fun evaluateError(e: Exception) = Resource.Failure(getErrorMessage(e))

    //todo use context.getString()
    private fun getErrorMessage(e: Exception) = when (e) {
        is UnknownHostException,
        is ConnectException,
        is SocketException -> "Please check your connection"
        is Timeout -> "Looks like the server is taking to long to respond, please try again in sometime"
        is HttpException -> {
            when(e.code()) {
                401 -> "Please login again"
                429 -> "Daily call quota exceeded. Please try tomorrow"
                else -> {
                    val json = e.response()?.errorBody()?.string()
                    val model = Gson().fromJson(json, SocialNetworkResponse::class.java)
                    model.meta.errorDetail
                }
            }
        }
        else -> e.message
    }
}