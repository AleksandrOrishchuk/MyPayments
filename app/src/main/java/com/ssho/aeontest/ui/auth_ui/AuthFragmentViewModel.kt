package com.ssho.aeontest.ui.auth_ui

import android.util.Log
import androidx.lifecycle.*
import com.ssho.aeontest.Navigator
import com.ssho.aeontest.data.datasource.AuthorizationError
import com.ssho.aeontest.data.model.AuthData
import com.ssho.aeontest.domain.service.AuthDataCacheManager
import com.ssho.aeontest.domain.usecase.AuthorizeUserUseCase
import com.ssho.aeontest.ui.model.AuthUiData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "AuthViewModel"

class AuthFragmentViewModel(
    private val authorizeUser: AuthorizeUserUseCase,
    private val authDataCacheManager: AuthDataCacheManager,
    private val navigator: Navigator
) : ViewModel() {
    val authUiData get() = _authUiData
    val authorizationViewState: LiveData<AuthorizationViewState> get() = _authorizationViewState
    private var _authUiData: AuthUiData = getInitialAuthUiData()
    private val _authorizationViewState: MutableLiveData<AuthorizationViewState> = MutableLiveData()

    init {
        postRegularLoggingViewState()
    }

    fun login() {
        viewModelScope.launch {
            postLoadingViewState()
            val authData = mapAuthData(authUiData)
            runCatching {
                withContext(Dispatchers.IO) {
                    authorizeUser(authData)
                }
            }.onSuccess {
                if (authUiData.isRememberMeChecked)
                    authDataCacheManager.cacheAuthData(authData)
                navigator.userPayments()
            }.onFailure { error ->
                Log.e(TAG, error.message, error)
                when (error) {
                    is AuthorizationError -> postFailedLoggingViewState()
                    else -> postNetworkErrorViewState()
                }
            }
        }
    }

    fun onLoginFieldUpdated(login: String) {
        _authUiData = _authUiData.copy(login = login)
    }

    fun onPasswordFieldUpdated(password: String) {
        _authUiData = _authUiData.copy(password = password)
    }

    fun onRememberMeChecked(isChecked: Boolean) {
        _authUiData = _authUiData.copy(isRememberMeChecked = isChecked)
    }

    private fun postRegularLoggingViewState() {
        _authorizationViewState.postValue(
            AuthorizationViewState.RegularLogging
        )
    }

    private fun postFailedLoggingViewState() {
        _authorizationViewState.postValue(
            AuthorizationViewState.FailedLogging
        )
    }

    private fun postNetworkErrorViewState() {
        _authorizationViewState.postValue(
            AuthorizationViewState.NetworkError
        )
    }

    private fun postLoadingViewState() {
        _authorizationViewState.postValue(
            AuthorizationViewState.Loading
        )
    }

    private fun getInitialAuthUiData(): AuthUiData {
        val cachedAuthData = authDataCacheManager.getAuthData()

        return if (cachedAuthData.isCached)
            mapAuthUiData(cachedAuthData)
        else
            AuthUiData()
    }

    private fun mapAuthUiData(authData: AuthData): AuthUiData {
        return if (authData.isCached)
            AuthUiData(
                login = authData.login,
                password = authData.password,
                isRememberMeChecked = true
            )
        else AuthUiData()
    }


    private fun mapAuthData(authUiData: AuthUiData): AuthData =
        AuthData(
            login = authUiData.login,
            password = authUiData.password,
            isCached = authUiData.isRememberMeChecked
        )

}


@Suppress("UNCHECKED_CAST")
class AuthFragmentViewModelFactory(
    private val authorizeUserUseCase: AuthorizeUserUseCase,
    private val authDataCacheManager: AuthDataCacheManager,
    private val navigator: Navigator
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AuthFragmentViewModel(
            authorizeUserUseCase,
            authDataCacheManager,
            navigator
        ) as T
    }

}

sealed class AuthorizationViewState {
    object RegularLogging : AuthorizationViewState()
    object FailedLogging : AuthorizationViewState()
    object NetworkError : AuthorizationViewState()
    object Loading : AuthorizationViewState()
}