package com.gturedi.socialnetworkapp.network

import com.google.gson.annotations.SerializedName

sealed class NetworkResult<out T> {
    data class Success<T>(val response: T?) : NetworkResult<T>()
    data class Failure<T>(val error: ServiceError) : NetworkResult<T>()
}

class ServiceError(val message: String? = null)

class TokenModel(
    @SerializedName("access_token") val token: String
)