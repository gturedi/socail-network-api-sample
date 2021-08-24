package com.gturedi.socialnetworkapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.gturedi.socialnetworkapp.BuildConfig
import com.gturedi.socialnetworkapp.network.AuthRetrofitApi
import com.gturedi.socialnetworkapp.network.SocialNetworkRetrofitApi
import com.gturedi.socialnetworkapp.util.AppConst
import com.gturedi.socialnetworkapp.util.PrefService
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

//@Module
//@InstallIn(SingletonComponent::class)
//object NetworkModule {
val networkModule = module {
    single { provideSocialNetworkService(get()) }
    single { provideAuthService(get()) }
    single { provideGson() }
}

//@Singleton
//@Provides
//fun provideSocialNetworkRepository(api: SocialNetworkRetrofitApi): SocialNetworkRepository = RemoteSocialNetworkRepository(api)

//@Singleton
//@Provides
//fun provideAuthRepository(api: AuthRetrofitApi): AuthRepository = RemoteAuthRepository(api)

//@Singleton
//@Provides
fun provideSocialNetworkService(prefService: PrefService) = Retrofit.Builder()
    .baseUrl(AppConst.URL_API)
    .addConverterFactory(GsonConverterFactory.create(provideGson()))
    .client(provideOkhttp({
        val original: Request = it.request()
        val originalHttpUrl: HttpUrl = original.url()

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("oauth_token", prefService.readAccessToken())
            .addQueryParameter("v", AppConst.API_VERS)
            .build()

        val requestBuilder = original.newBuilder().url(url)

        val request = requestBuilder.build()
        it.proceed(request)
    }))
    .build()
    .create(SocialNetworkRetrofitApi::class.java)

//@Singleton
//@Provides
fun provideAuthService(gson: Gson) = Retrofit.Builder()
    .baseUrl(AppConst.URL_BASE)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .client(provideOkhttp())
    .build()
    .create(AuthRetrofitApi::class.java)

//@Singleton
//@Provides
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

//@Singleton
//@Provides
fun provideGson() = GsonBuilder()
    .disableHtmlEscaping()
    .serializeNulls()
    .create()
//}