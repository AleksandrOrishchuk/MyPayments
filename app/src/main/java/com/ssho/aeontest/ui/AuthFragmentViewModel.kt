package com.ssho.aeontest.ui

import android.util.Log
import androidx.lifecycle.*
import com.ssho.aeontest.Navigator
import com.ssho.aeontest.data.AuthorizationError
import com.ssho.aeontest.domain.service.AuthDataCacheManager
import com.ssho.aeontest.domain.usecase.AuthorizeUserUseCase
import com.ssho.aeontest.ui.model.AuthUiData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "AuthViewModel"

class AuthFragmentViewModel(
    private val authorizeUser: AuthorizeUserUseCase,
    private val authDataRememberMeManager: AuthDataCacheManager,
    private val navigator: Navigator
) : ViewModel() {
    val authUiData get() = _authUiData
    val authorizationViewState: LiveData<AuthorizationViewState> get() = _authorizationViewState
    private var _authUiData: AuthUiData = authDataRememberMeManager.getAuthUiData()
    private val _authorizationViewState: MutableLiveData<AuthorizationViewState> = MutableLiveData()

init {
    postRegularLoggingViewState()
}

    fun login() {
        viewModelScope.launch {
            postLoadingViewState()
            runCatching {
                withContext(Dispatchers.IO) {
                    authDataRememberMeManager.rememberOrClearCachedAuthData(authUiData)
                    authorizeUser(authUiData)
                }
            }.onSuccess {
                navigator.userPayments()
            }.onFailure { error ->
                if (error is AuthorizationError)
                    postFailedLoggingViewState()
                else
                    postNetworkErrorViewState()
                Log.e(TAG, error.message, error)
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