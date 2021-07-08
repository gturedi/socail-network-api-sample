package com.gturedi.socialnetworkapp.network

import com.google.gson.GsonBuilder
import com.gturedi.socialnetworkapp.BuildConfig
import com.gturedi.socialnetworkapp.util.AppConst
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object AuthApi {

    private val httpClient:OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(AppConst.CONN_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            })
            .build()
    }

    val service: AuthService by lazy {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(AppConst.SERVICE_URL_AUTH)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            //.addCallAdapterFactory(PazaramaNetworkCallAdapterFactory())
            .client(httpClient)
            .build()

        retrofit.create(AuthService::class.java)
    }

}