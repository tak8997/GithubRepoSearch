package com.tak8997.githubreposearch.di

import com.tak8997.githubreposearch.data.remote.ApiService
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModules = module {
    single {
        get<Retrofit>().create(ApiService::class.java)
    }
}