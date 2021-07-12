package com.gturedi.socialnetworkapp.network.repository

import com.gturedi.socialnetworkapp.network.model.CheckinReponseModel
import com.gturedi.socialnetworkapp.network.model.Resource
import com.gturedi.socialnetworkapp.network.model.SocialNetworkResponse
import com.gturedi.socialnetworkapp.network.model.VenueResponseModel

interface SocialNetworkRepository {

    suspend fun retrieveCheckins(): Resource<SocialNetworkResponse<CheckinReponseModel>>

    suspend fun retrieveVenue(id: String): Resource<SocialNetworkResponse<VenueResponseModel>>
}