package com.gturedi.socialnetworkapp

import android.app.Application

class App : Application() {

    companion object {
        var ctx: App? = null
    }

    override fun onCreate() {
        super.onCreate()
        ctx = this
    }
}
