package com.gturedi.socialnetworkapp.network

import com.gturedi.socialnetworkapp.network.model.CheckinReponseModel
import com.gturedi.socialnetworkapp.network.model.NetworkResult
import com.gturedi.socialnetworkapp.network.model.SocialNetworkResponse
import com.gturedi.socialnetworkapp.network.model.VenueResponseModel
import javax.inject.Inject

class SocialNetworkRepository @Inject constructor(
    private val service: SocialNetworkService
) {

    suspend fun retrieveCheckins(): NetworkResult<SocialNetworkResponse<CheckinReponseModel>> = try {
        val result = service.checkins()
        NetworkResult.Success(result)
    } catch (e: Exception) {
        NetworkResult.Failure(e.message)
    }

    suspend fun retrieveVenue(id: String): NetworkResult<SocialNetworkResponse<VenueResponseModel>> = try {
        val result = service.venues(id)
        NetworkResult.Success(result)
    } catch (e: Exception) {
        NetworkResult.Failure(e.message)
    }

}