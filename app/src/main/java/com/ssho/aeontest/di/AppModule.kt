package com.ssho.aeontest.di

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.ssho.aeontest.Navigator
import com.ssho.aeontest.data.*
import com.ssho.aeontest.data.api.RemoteServerApi
import com.ssho.aeontest.data.datasource.PaymentsRemoteDataSource
import com.ssho.aeontest.data.repository.PaymentsRepository
import com.ssho.aeontest.data.repository.PaymentsRepositoryImpl
import com.ssho.aeontest.domain.service.AuthDataCacheManager
import com.ssho.aeontest.domain.service.AuthDataCacheManagerImpl
import com.ssho.aeontest.domain.service.AuthDataUpdaterImpl
import com.ssho.aeontest.domain.service.AuthUiDataProviderImpl
import com.ssho.aeontest.ui.AuthFragmentViewModelFactory
import com.ssho.aeontest.domain.usecase.*
import com.ssho.aeontest.ui.AuthUiDataMapper
import com.ssho.aeontest.ui.PaymentListFragmentViewModelFactory
import com.ssho.aeontest.ui.PaymentUiMapper
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

    private val authDataMapper: AuthDataMapper by lazy {
        AuthDataMapper()
    }

    private val authUiDataMapper: AuthUiDataMapper by lazy {
        AuthUiDataMapper()
    }

    private val authorizeUserUseCase: AuthorizeUserUseCase by lazy {
        AuthorizeUserUseCaseImpl(
            authRepository = authRepository,
            userRepository = userRepository,
            authDataMapper = authDataMapper
        )
    }

    private val unauthorizeUserUseCase: UnauthorizeUserUseCase by lazy {
        UnauthorizeUserUseCaseImpl(
            userRepository = userRepository
        )
    }

    private val authDataCacheManager: AuthDataCacheManager by lazy {
        AuthDataCacheManagerImpl(
            AuthUiDataProviderImpl(
                authRepository = authRepository,
                authUiDataMapper = authUiDataMapper
            ),
            AuthDataUpdaterImpl(
                authRepository = authRepository,
                authDataMapper = authDataMapper
            )
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