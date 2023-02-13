package com.example.domain.di

import com.example.domain.action.useCase.ActionUseCase
import org.koin.dsl.module

val koinDomainModule = module {
    single { ActionUseCase(get()) }
}