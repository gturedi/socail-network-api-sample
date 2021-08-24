package com.gturedi.socialnetworkapp

import android.app.Application
import com.gturedi.socialnetworkapp.di.appModule
import com.gturedi.socialnetworkapp.di.networkModule
import com.gturedi.socialnetworkapp.di.repositoryModule
import com.gturedi.socialnetworkapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

//@HiltAndroidApp
class App : Application() {

    companion object {
        var ctx: App? = null
    }

    override fun onCreate() {
        super.onCreate()
        ctx = this

        startKoin {
            if (BuildConfig.DEBUG) androidLogger()
            androidContext(this@App)
            modules(appModule, networkModule, repositoryModule, viewModelModule)
        }
    }
}
