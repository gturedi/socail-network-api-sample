package com.gturedi.socialnetworkapp.network

import com.gturedi.socialnetworkapp.network.model.CheckinReponseModel
import com.gturedi.socialnetworkapp.network.model.SocialNetworkResponse
import com.gturedi.socialnetworkapp.network.model.VenueResponseModel
import retrofit2.http.GET
import retrofit2.http.Path

interface SocialNetworkRetrofitApi {

    @GET("users/self/checkins")
    suspend fun checkins(): SocialNetworkResponse<CheckinReponseModel>

    @GET("venues/{id}")
    suspend fun venues(@Path("id") id: String): SocialNetworkResponse<VenueResponseModel>

}