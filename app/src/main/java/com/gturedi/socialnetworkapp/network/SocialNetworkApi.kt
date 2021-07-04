package com.gturedi.socialnetworkapp.network

import com.google.gson.GsonBuilder
import com.gturedi.socialnetworkapp.util.AppConst
import com.gturedi.socialnetworkapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object SocialNetworkApi {

    private const val TIMEOUT_SECOND = 60L

    private val httpClient:OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_SECOND, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            })
            .build()
    }

    val socialNetworkService: SocialNetworkService by lazy {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(AppConst.API_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            //.addCallAdapterFactory(PazaramaNetworkCallAdapterFactory())
            .client(httpClient)
            .build()

        retrofit.create(SocialNetworkService::class.java)
    }

}