package com.example.data.di

import android.util.Log.v
import com.example.data.action.ActionRepositoryImpl
import com.example.data.common.ApiService
import com.example.data.common.Constants
import com.example.domain.action.ActionRepository
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

private const val TIME_OUT = 30L
val koinDataModule = module {
    single {
        HttpLoggingInterceptor { message -> v("HTTP Logger", message) }.apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.BODY
            }
        }
    }

    single(named("OkHttpClient")) {
        OkHttpClient.Builder()
            .cache(
                Cache(
                    directory = File(androidContext().externalCacheDir, "buttonToAction-cache"),
                    maxSize = 10 * 1024 * 1024
                )
            )
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single<ApiService> {
        val gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .create()

        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(get(named("OkHttpClient")))
            .build()
            .create(ApiService::class.java)
    }

    single<ActionRepository> {
        ActionRepositoryImpl(get())
    }
}