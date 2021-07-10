package com.gturedi.socialnetworkapp.network

import com.gturedi.socialnetworkapp.network.model.CheckinReponseModel
import com.gturedi.socialnetworkapp.network.model.Resource
import com.gturedi.socialnetworkapp.network.model.SocialNetworkResponse
import com.gturedi.socialnetworkapp.network.model.VenueResponseModel
import javax.inject.Inject

class SocialNetworkRepository @Inject constructor(
    private val retrofitApi: SocialNetworkRetrofitApi
) : BaseRepository() {

    suspend fun retrieveCheckins(): Resource<SocialNetworkResponse<CheckinReponseModel>> = try {
        val result = retrofitApi.checkins()
        Resource.Success(result)
    } catch (e: Exception) {
        evaluateError(e)
    }

    suspend fun retrieveVenue(id: String): Resource<SocialNetworkResponse<VenueResponseModel>> = try {
        val result = retrofitApi.venues(id)
        Resource.Success(result)
    } catch (e: Exception) {
        evaluateError(e)
    }

}