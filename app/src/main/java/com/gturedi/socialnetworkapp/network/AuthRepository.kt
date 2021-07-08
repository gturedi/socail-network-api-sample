package com.gturedi.socialnetworkapp.network

import com.gturedi.socialnetworkapp.BuildConfig
import com.gturedi.socialnetworkapp.network.*
import com.gturedi.socialnetworkapp.network.model.*
import com.gturedi.socialnetworkapp.util.AppConst

class AuthRepository {

    private val service by lazy { AuthApi.service }

    suspend fun retrieveAccessToken(code: String): NetworkResult<TokenModel> = try {
        val result = service.accessToken(BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET,
            BuildConfig.REDIRECT_URL, AppConst.GRANT_TYPE, code)
        NetworkResult.Success(result)
    } catch (e:Exception) {
        NetworkResult.Failure(e.message)
    }

}