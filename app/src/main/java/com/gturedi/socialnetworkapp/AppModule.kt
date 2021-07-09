package com.gturedi.socialnetworkapp

import com.google.gson.GsonBuilder
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
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideSocialNetworkRepository() = SocialNetworkRepository(provideSocialNetworkService())

    @Provides
    fun provideAuthRepository() = AuthRepository(provideAuthService())

    @Provides
    fun provideSocialNetworkService(): SocialNetworkService {
        val interceptor: (Interceptor.Chain) -> Response = {
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

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(AppConst.URL_API)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(provideOkhttp(interceptor))
            .build()

        return retrofit.create(SocialNetworkService::class.java)
    }

    @Provides
    fun provideAuthService(): AuthService {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(AppConst.URL_BASE)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(provideOkhttp())
            .build()

        return retrofit.create(AuthService::class.java)
    }

    @Provides
    fun provideOkhttp(vararg interceptors: Interceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(AppConst.CONN_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            })

        interceptors.forEach {
            builder.addInterceptor(it)
        }

        return builder.build()
    }

}