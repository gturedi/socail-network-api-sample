package com.gturedi.socialnetworkapp.network

import com.gturedi.socialnetworkapp.network.model.TokenModel
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthRetroFitApi {

    @GET("access_token")
    suspend fun accessToken(
        @Query("client_id") client_id: String,
        @Query("client_secret") client_secret: String,
        @Query("redirect_uri") redirect_uri: String,
        @Query("grant_type") grant_type: String,
        @Query("code") code: String
    ): TokenModel

}