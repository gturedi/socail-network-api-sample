package com.gturedi.socialnetworkapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.gturedi.socialnetworkapp.BuildConfig
import com.gturedi.socialnetworkapp.network.AuthRepository
import com.gturedi.socialnetworkapp.network.AuthService
import com.gturedi.socialnetworkapp.network.SocialNetworkRepository
import com.gturedi.socialnetworkapp.network.SocialNetworkService
import com.gturedi.socialnetworkapp.util.AppConst
import com.gturedi.socialnetworkapp.util.PrefService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideSocialNetworkRepository(service:SocialNetworkService) = SocialNetworkRepository(service)

    @Singleton
    @Provides
    fun provideAuthRepository(service: AuthService) = AuthRepository(service)

    @Singleton
    @Provides
    fun provideSocialNetworkService(prefService: PrefService) = Retrofit.Builder()
        .baseUrl(AppConst.URL_API)
        .addConverterFactory(GsonConverterFactory.create(provideGson()))
        .client(provideOkhttp({
            val original: Request = it.request()
            val originalHttpUrl: HttpUrl = original.url()

            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("oauth_token", prefService.accessToken())
                .addQueryParameter("v", AppConst.API_VERS)
                .build()

            val requestBuilder = original.newBuilder().url(url)

            val request = requestBuilder.build()
            it.proceed(request)
        }))
        .build()
        .create(SocialNetworkService::class.java)

    @Singleton
    @Provides
    fun provideAuthService(gson:Gson) = Retrofit.Builder()
        .baseUrl(AppConst.URL_BASE)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(provideOkhttp())
        .build()
        .create(AuthService::class.java)

    @Singleton
    @Provides
    fun provideOkhttp(vararg interceptors: Interceptor = emptyArray()) = OkHttpClient.Builder()
        .connectTimeout(AppConst.CONN_TIMEOUT, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        })
        .apply {
            interceptors.forEach {
                addInterceptor(it)
            }
        }
        .build()

    @Singleton
    @Provides
    fun provideGson() = GsonBuilder()
        .disableHtmlEscaping()
        .serializeNulls()
        .create()
}