package com.tak8997.githubreposearch

import android.app.Application
import com.tak8997.githubreposearch.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(networkModule))
        }
    }
}