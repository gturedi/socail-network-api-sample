package com.gturedi.socialnetworkapp

import android.app.Application

class App : Application() {

    companion object {
        lateinit var ctx:App
    }

    override fun onCreate() {
        super.onCreate()
        ctx = this
    }
}
