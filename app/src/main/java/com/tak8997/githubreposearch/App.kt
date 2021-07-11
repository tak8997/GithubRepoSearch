package com.tak8997.githubreposearch

import android.app.Application
import com.tak8997.githubreposearch.di.apiModules
import com.tak8997.githubreposearch.di.dataModules
import com.tak8997.githubreposearch.di.networkModules
import com.tak8997.githubreposearch.di.presentationModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(networkModules, dataModules, presentationModules, apiModules))
        }
    }
}