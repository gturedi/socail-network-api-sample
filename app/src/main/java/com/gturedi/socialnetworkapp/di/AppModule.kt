package com.gturedi.socialnetworkapp.di

import android.app.Application
import android.content.Context
import com.gturedi.socialnetworkapp.util.PrefService
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


val appModule = module {
    single { PrefService(providePreferences(androidApplication())) }
}

private const val DEFAULT_PREF = "DEFAULT_PREF"

private fun providePreferences(app: Application) =
    app.getSharedPreferences(DEFAULT_PREF, Context.MODE_PRIVATE)