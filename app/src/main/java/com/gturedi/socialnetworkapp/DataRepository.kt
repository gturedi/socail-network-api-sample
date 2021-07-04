package com.gturedi.socialnetworkapp

import com.gturedi.socialnetworkapp.network.SocialNetworkApi
import com.gturedi.socialnetworkapp.network.TokenModel

class DataRepository {

    val service by lazy { SocialNetworkApi.socialNetworkService }

    suspend fun retrieveAccessToken(
        code: String
    ): TokenModel {
        return service.getAccessToken(code)
    }

}