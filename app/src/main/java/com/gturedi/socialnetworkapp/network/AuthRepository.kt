package com.gturedi.socialnetworkapp.network

import com.gturedi.socialnetworkapp.BuildConfig
import com.gturedi.socialnetworkapp.network.model.Resource
import com.gturedi.socialnetworkapp.network.model.TokenModel
import com.gturedi.socialnetworkapp.util.AppConst
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val retroFitApi: AuthRetroFitApi
) : BaseRepository() {

    suspend fun retrieveAccessToken(code: String): Resource<TokenModel> = try {
        val result = retroFitApi.accessToken(
            BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET,
            BuildConfig.REDIRECT_URL, AppConst.GRANT_TYPE, code
        )
        Resource.Success(result)
    } catch (e: Exception) {
        evaluateError(e)
    }

}