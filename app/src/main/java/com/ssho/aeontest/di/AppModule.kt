package com.ssho.aeontest.di

import android.content.Context
import com.ssho.aeontest.data.api.RemoteServerApi
import retrofit2.Retrofit

class AppModule {

    lateinit var androidContext: Context

    private val remoteServerApi: RemoteServerApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://82.202.204.94/api/")
            .build()
        retrofit.create(RemoteServerApi::class.java)
    }
}