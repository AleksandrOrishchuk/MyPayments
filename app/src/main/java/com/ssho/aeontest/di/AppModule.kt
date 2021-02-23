package com.ssho.aeontest.di

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.ssho.aeontest.Navigator
import com.ssho.aeontest.data.api.RemoteServerApi
import com.ssho.aeontest.data.datasource.*
import com.ssho.aeontest.data.repository.*
import com.ssho.aeontest.domain.service.*
import com.ssho.aeontest.ui.auth_ui.AuthFragmentViewModelFactory
import com.ssho.aeontest.domain.usecase.*
import com.ssho.aeontest.ui.payments_ui.PaymentListFragmentViewModelFactory
import com.ssho.aeontest.ui.payments_ui.PaymentUiMapper
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

    private val authRemoteDataSource: AuthRemoteDataSource by lazy {
        AuthRemoteDataSourceImpl(
            remoteServerApi = remoteServerApi
        )
    }

    private val authLocalDataSource: AuthLocalDataSource by lazy {
        AuthLocalDataSource(
            sharedPreferences = sharedPrefs
        )
    }

    private val authRepository: AuthRepository by lazy {
        AuthRepositoryImpl(
            authLocalDataSource = authLocalDataSource,
            authRemoteDataSource = authRemoteDataSource
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

    private val paymentsRemoteDataSource: PaymentsRemoteDataSource by lazy {
        PaymentsRemoteDataSource(
            remoteServerApi
        )
    }

    private val paymentsRepository: PaymentsRepository by lazy {
        PaymentsRepositoryImpl(
            paymentsRemoteDataSource
        )
    }

    private val authorizeUserUseCase: AuthorizeUserUseCase by lazy {
        AuthorizeUserUseCaseImpl(
            authRepository = authRepository,
            userRepository = userRepository,
        )
    }

    private val unauthorizeUserUseCase: UnauthorizeUserUseCase by lazy {
        UnauthorizeUserUseCaseImpl(
            userRepository = userRepository
        )
    }

    private val authDataCacheManager: AuthDataCacheManager by lazy {
        AuthDataCacheManagerImpl(
            authRepository = authRepository,
        )
    }

    private val getUserPaymentsUseCase: GetUserPaymentsUseCase by lazy {
        GetUserPaymentsUseCaseImpl(
            getCurrentUser = getCurrentUserUseCase,
            paymentsRepository = paymentsRepository
        )
    }

    internal val getCurrentUserUseCase: GetCurrentUserUseCase by lazy {
        GetCurrentUserUseCaseImpl(
            userRepository
        )
    }

    internal fun provideAuthViewModelFactory(): AuthFragmentViewModelFactory =
        AuthFragmentViewModelFactory(
            authorizeUserUseCase = authorizeUserUseCase,
            authDataCacheManager = authDataCacheManager,
            navigator = navigator
        )

    internal fun providePaymentListViewModelFactory(): PaymentListFragmentViewModelFactory =
        PaymentListFragmentViewModelFactory(
            paymentUiMapper = PaymentUiMapper(),
            getUserPaymentsUseCase = getUserPaymentsUseCase,
            unauthorizeUser = unauthorizeUserUseCase,
            navigator = navigator
        )
}