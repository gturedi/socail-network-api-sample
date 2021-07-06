package com.gturedi.socialnetworkapp.network

import com.google.gson.GsonBuilder
import com.gturedi.socialnetworkapp.BuildConfig
import com.gturedi.socialnetworkapp.util.AppConst
import com.gturedi.socialnetworkapp.util.PrefService
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object SocialNetworkApi {

    private val httpClient:OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(AppConst.CONN_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            })
            .addInterceptor {
                val original: Request = it.request()
                val originalHttpUrl: HttpUrl = original.url()

                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("oauth_token", PrefService.accessToken())
                    .addQueryParameter("v", AppConst.API_VERS)
                    .build()

                val requestBuilder = original.newBuilder().url(url)

                val request = requestBuilder.build()
                it.proceed(request)
            }
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