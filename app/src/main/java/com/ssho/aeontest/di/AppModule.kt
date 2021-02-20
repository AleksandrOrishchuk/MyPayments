package com.ssho.aeontest.di

import android.annotation.SuppressLint
import android.content.Context
import android.preference.PreferenceManager
import com.ssho.aeontest.data.*
import com.ssho.aeontest.data.api.RemoteServerApi
import retrofit2.Retrofit

@SuppressLint("StaticFieldLeak")
internal object AppModule {

    lateinit var androidContext: Context

    private val remoteServerApi: RemoteServerApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://82.202.204.94/api/")
            .build()
        retrofit.create(RemoteServerApi::class.java)
    }

    private val userLocalDataSource: UserLocalDataSource by lazy {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(androidContext)
        UserLocalDataSource(sharedPrefs)
    }

    private val userRemoteDataSource: UserRemoteDataSource by lazy {
        UserRemoteDataSourceImpl(
            remoteServerApi = remoteServerApi
        )
    }

    private val userRepository: UserRepository by lazy {
        UserRepositoryImpl(
            userLocalDataSource,
            userRemoteDataSource
        )
    }
}