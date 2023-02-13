package com.example.buttontoaction

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.buttontoaction.di.koinAppModule
import com.example.data.di.koinDataModule
import com.example.domain.di.koinDomainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(koinAppModule, koinDataModule, koinDomainModule))
        }
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }
}