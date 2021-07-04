package com.gturedi.socialnetworkapp.network

import com.gturedi.socialnetworkapp.util.AppConst
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SocialNetworkService {

    @GET("${AppConst.TOKEN_URL}")
    suspend fun getAccessToken(@Query("code") code: String): TokenModel

}