package com.gturedi.socialnetworkapp.network

import com.gturedi.socialnetworkapp.util.AppConst
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SocialNetworkService {

    @GET("${AppConst.TOKEN_URL}")
    suspend fun accessToken(@Query("code") code: String): TokenModel

    @GET("users/self/checkins")
    suspend fun checkins(): SocialNetworkResponse<CheckinReponseModel>

    @GET("venues/{id}")
    suspend fun venues(@Path("id") id: String): SocialNetworkResponse<VenueResponseModel>

}