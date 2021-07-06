package com.gturedi.socialnetworkapp.ui

import com.gturedi.socialnetworkapp.network.*
import com.gturedi.socialnetworkapp.network.model.*

class DataRepository {

    val service by lazy { SocialNetworkApi.socialNetworkService }

    suspend fun retrieveAccessToken(code: String): NetworkResult<TokenModel> = try {
        val result = service.accessToken(code)
        NetworkResult.Success(result)
    } catch (e:Exception) {
        NetworkResult.Failure(e.message)
    }

    suspend fun retrieveCheckins(): NetworkResult<SocialNetworkResponse<CheckinReponseModel>> = try {
        val result = service.checkins()
        NetworkResult.Success(result)
    } catch (e:Exception) {
        NetworkResult.Failure(e.message)
    }

    suspend fun retrieveVenue(id:String): NetworkResult<SocialNetworkResponse<VenueResponseModel>> = try {
        val result = service.venues(id)
        NetworkResult.Success(result)
    } catch (e:Exception) {
        NetworkResult.Failure(e.message)
    }

}