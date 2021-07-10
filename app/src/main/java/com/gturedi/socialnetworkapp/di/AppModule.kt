package com.gturedi.socialnetworkapp.di

import android.content.Context
import android.content.SharedPreferences
import com.gturedi.socialnetworkapp.util.PrefService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePrefService(pref:SharedPreferences) = PrefService(pref)

    @Singleton
    @Provides
    fun providePref(@ApplicationContext ctx: Context) = ctx.getSharedPreferences("pref", Context.MODE_PRIVATE)
}
