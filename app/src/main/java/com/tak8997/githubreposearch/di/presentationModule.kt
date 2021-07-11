package com.tak8997.githubreposearch.di

import com.tak8997.githubreposearch.ui.RepoSearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val presentationModules = module {
    viewModel { RepoSearchViewModel(it[0], get()) }
}