package com.ssho.aeontest.di

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.ssho.aeontest.Navigator
import com.ssho.aeontest.data.*
import com.ssho.aeontest.data.api.RemoteServerApi
import com.ssho.aeontest.ui.LoginFragmentViewModelFactory
import com.ssho.aeontest.usecase.*
import retrofit2.Retrofit

@SuppressLint("StaticFieldLeak")
internal object AppModule {

    lateinit var androidContext: Context

    val navigator: Navigator by lazy {
        Navigator()
    }

    private val sharedPrefs: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(androidContext)
    }

    private val remoteServerApi: RemoteServerApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://82.202.204.94/api/")
            .build()
        retrofit.create(RemoteServerApi::class.java)
    }

    private val loginRemoteDataSource: LoginRemoteDataSource by lazy {
        LoginRemoteDataSourceImpl(
            remoteServerApi = remoteServerApi
        )
    }

    private val loginLocalDataSource: LoginLocalDataSource by lazy {
        LoginLocalDataSource(
            sharedPreferences = sharedPrefs
        )
    }

    private val loginDataMappers: LoginDataMappers by lazy {
        LoginDataMappers()
    }

    private val loginRepository: LoginRepository by lazy {
        LoginRepositoryImpl(
            loginLocalDataSource = loginLocalDataSource,
            loginRemoteDataSource = loginRemoteDataSource
        )
    }

    private val userLocalDataSource: UserLocalDataSource by lazy {
        UserLocalDataSource(
            sharedPreferences = sharedPrefs
        )
    }

    private val userRepository: UserRepository by lazy {
        UserRepositoryImpl(
            userLocalDataSource
        )
    }

    private val userDataMappers: UserDataMappers by lazy {
        UserDataMappers()
    }

    private val loginUseCase: LoginUseCase by lazy {
        LoginUseCaseImpl(
            loginRepository = loginRepository,
            loginDataMappers = loginDataMappers,
            userRepository = userRepository,
            userDataMappers = userDataMappers
        )
    }

    private val getCachedLoginUseCase: GetCachedLoginUseCase by lazy {
        GetCachedLoginUseCaseImpl(
            loginRepository,
            loginDataMappers
        )
    }

    private val getCurrentUserUseCase: GetCurrentUserUseCase by lazy {
        GetCurrentUserUseCaseImpl(
            userRepository
        )
    }

    internal fun isUserLoggedIn(): Boolean = userRepository.isUserLoggedIn()

    internal fun provideLoginViewModelFactory(): LoginFragmentViewModelFactory =
        LoginFragmentViewModelFactory(
            loginUseCase = loginUseCase,
            getCachedLoginUseCase = getCachedLoginUseCase,
            navigator = navigator
        )
}