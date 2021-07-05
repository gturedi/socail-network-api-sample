package com.gturedi.socialnetworkapp

import com.gturedi.socialnetworkapp.network.NetworkResult
import com.gturedi.socialnetworkapp.network.ServiceError
import com.gturedi.socialnetworkapp.network.SocialNetworkApi
import com.gturedi.socialnetworkapp.network.TokenModel

class DataRepository {

    val service by lazy { SocialNetworkApi.socialNetworkService }

    suspend fun retrieveAccessToken(code: String): NetworkResult<TokenModel> = try {
        val result = service.getAccessToken(code)
        NetworkResult.Success(result)
    } catch (e:Exception) {
        NetworkResult.Failure(ServiceError(e.message))
    }

}