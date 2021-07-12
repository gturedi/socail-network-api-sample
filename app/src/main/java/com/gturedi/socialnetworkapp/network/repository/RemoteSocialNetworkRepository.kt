package com.gturedi.socialnetworkapp.network.repository

import com.gturedi.socialnetworkapp.network.SocialNetworkRetrofitApi
import com.gturedi.socialnetworkapp.network.model.CheckinReponseModel
import com.gturedi.socialnetworkapp.network.model.Resource
import com.gturedi.socialnetworkapp.network.model.SocialNetworkResponse
import com.gturedi.socialnetworkapp.network.model.VenueResponseModel
import javax.inject.Inject

class RemoteSocialNetworkRepository @Inject constructor(
    private val api: SocialNetworkRetrofitApi
) : BaseRepository(), SocialNetworkRepository {

    override suspend fun retrieveCheckins(): Resource<SocialNetworkResponse<CheckinReponseModel>> = try {
        val result = api.checkins()
        Resource.Success(result)
    } catch (e: Exception) {
        evaluateError(e)
    }

    override suspend fun retrieveVenue(id: String): Resource<SocialNetworkResponse<VenueResponseModel>> = try {
        val result = api.venues(id)
        Resource.Success(result)
    } catch (e: Exception) {
        evaluateError(e)
    }

}