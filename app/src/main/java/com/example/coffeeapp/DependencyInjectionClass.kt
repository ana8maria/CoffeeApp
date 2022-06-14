package com.example.coffeeapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DependencyInjectionClass: Application() {

    override fun onCreate(){
        super.onCreate()
        startKoin {
            androidContext(this@DependencyInjectionClass)
            modules(listOf(appModule))
        }
    }
}