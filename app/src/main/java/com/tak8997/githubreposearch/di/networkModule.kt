package com.tak8997.githubreposearch.di

import com.tak8997.githubreposearch.data.remote.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val BASE_URL = "https://api.github.com"

val networkModule = module {
    single { provideOkHttp() }
    single { provideRetrofit(get(), BASE_URL) }
    single { provideFeedService(get()) }
}

private fun provideFeedService(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
}

private fun provideOkHttp(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

    return OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()
}

private fun provideRetrofit(okHttpClient: OkHttpClient, url: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
