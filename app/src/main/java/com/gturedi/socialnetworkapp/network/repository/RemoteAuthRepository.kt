package com.gturedi.socialnetworkapp.network.repository

import com.gturedi.socialnetworkapp.BuildConfig
import com.gturedi.socialnetworkapp.network.AuthRetrofitApi
import com.gturedi.socialnetworkapp.network.model.Resource
import com.gturedi.socialnetworkapp.network.model.TokenModel
import com.gturedi.socialnetworkapp.util.AppConst

class RemoteAuthRepository /*@Inject */constructor(
    private val api: AuthRetrofitApi
) : BaseRepository(), AuthRepository {

    override suspend fun retrieveAccessToken(code: String): Resource<TokenModel> = try {
        val result = api.accessToken(
            BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET,
            BuildConfig.REDIRECT_URL, AppConst.GRANT_TYPE, code
        )
        Resource.Success(result)
    } catch (e: Exception) {
        evaluateError(e)
    }

}