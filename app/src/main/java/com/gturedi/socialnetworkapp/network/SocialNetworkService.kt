package com.gturedi.socialnetworkapp.network

import com.gturedi.socialnetworkapp.util.AppConst
import retrofit2.http.GET
import retrofit2.http.Query

interface SocialNetworkService {

    @GET("${AppConst.TOKEN_URL}")
    suspend fun accessToken(@Query("code") code: String): TokenModel

    @GET("checkins")
    suspend fun checkins(@Query("oauth_token") code: String): SocialNetworkResponse<CheckinReponseModel>

}