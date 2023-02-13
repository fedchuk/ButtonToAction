package com.example.buttontoaction.di

import com.example.buttontoaction.ui.action.ActionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val koinAppModule = module {
    viewModel { ActionViewModel(get()) }
}