package com.gturedi.socialnetworkapp.ui.home

import com.gturedi.socialnetworkapp.network.*

class DataRepository {

    val service by lazy { SocialNetworkApi.socialNetworkService }

    suspend fun retrieveAccessToken(code: String): NetworkResult<TokenModel> = try {
        val result = service.accessToken(code)
        NetworkResult.Success(result)
    } catch (e:Exception) {
        NetworkResult.Failure(ServiceError(e.message))
    }

    suspend fun retrieveCheckins(): NetworkResult<SocialNetworkResponse<CheckinReponseModel>> = try {
        val result = service.checkins("")
        NetworkResult.Success(result)
    } catch (e:Exception) {
        NetworkResult.Failure(ServiceError(e.message))
    }

}