package com.ssho.aeontest

import android.app.Application
import com.ssho.aeontest.di.AppModule

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        AppModule.androidContext = applicationContext

    }
}