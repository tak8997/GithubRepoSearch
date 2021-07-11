package com.tak8997.githubreposearch.di

import com.tak8997.githubreposearch.data.repository.RepoDataRepository
import com.tak8997.githubreposearch.data.repository.RepoRepository
import org.koin.dsl.module

val dataModules = module {
    single<RepoRepository> { RepoDataRepository() }
}