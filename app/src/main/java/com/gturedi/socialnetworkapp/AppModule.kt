package com.gturedi.socialnetworkapp

import android.content.Context
import android.content.SharedPreferences
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
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideSocialNetworkRepository(service:SocialNetworkService) = SocialNetworkRepository(service)

    @Provides
    fun provideAuthRepository(service: AuthService) = AuthRepository(service)

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

    @Provides
    fun provideAuthService() = Retrofit.Builder()
        .baseUrl(AppConst.URL_BASE)
        .addConverterFactory(GsonConverterFactory.create(provideGson()))
        .client(provideOkhttp())
        .build()
        .create(AuthService::class.java)

    @Provides
    fun provideOkhttp(vararg interceptors: Interceptor) = OkHttpClient.Builder()
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

    @Provides
    fun provideGson() = GsonBuilder()
        .disableHtmlEscaping()
        .serializeNulls()
        .create()

    @Provides
    fun providePrefService(pref:SharedPreferences) = PrefService(pref)

    @Provides
    fun providePref(@ApplicationContext ctx: Context) = ctx.getSharedPreferences("pref", Context.MODE_PRIVATE)
}