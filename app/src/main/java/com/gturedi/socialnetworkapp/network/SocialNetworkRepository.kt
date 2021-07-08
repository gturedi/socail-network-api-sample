package com.gturedi.socialnetworkapp.network

import com.gturedi.socialnetworkapp.network.model.*

class SocialNetworkRepository {

    private val service by lazy { SocialNetworkApi.service }

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