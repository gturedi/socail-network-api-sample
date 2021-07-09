package com.gturedi.socialnetworkapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    companion object {
        var ctx: App? = null
    }

    override fun onCreate() {
        super.onCreate()
        ctx = this
    }
}
