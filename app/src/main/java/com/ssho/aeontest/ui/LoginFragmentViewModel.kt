package com.ssho.aeontest.ui

import androidx.lifecycle.*
import com.ssho.aeontest.Navigator
import com.ssho.aeontest.data.LoginFailureException
import com.ssho.aeontest.usecase.GetCachedLoginUseCase
import com.ssho.aeontest.usecase.LoginUseCase
import com.ssho.aeontest.ui.model.LoginUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragmentViewModel(
    private val loginUseCase: LoginUseCase,
    getCachedLogin: GetCachedLoginUseCase,
    private val navigator: Navigator
) : ViewModel() {
    val loginUi get() = _loginUi
    val loginViewState: LiveData<LoginViewState> get() = _loginViewState
    private var _loginUi: LoginUi = getCachedLogin()
    private val _loginViewState: MutableLiveData<LoginViewState> = MutableLiveData()

init {
    postRegularLoggingViewState()
}

    fun login() {
        viewModelScope.launch {
            postLoadingViewState()
            runCatching {
                withContext(Dispatchers.IO) {
                    loginUseCase(loginUi)
                }
            }.onSuccess {
                navigator.successfulLogin()
            }.onFailure { error ->
                if (error is LoginFailureException)
                    postFailedLoggingViewState()
                else
                    postNetworkErrorViewState()
            }
        }
    }

    fun onLoginFieldUpdated(login: String) {
        _loginUi = _loginUi.copy(login = login)
    }

    fun onPasswordFieldUpdated(password: String) {
        _loginUi = _loginUi.copy(password = password)
    }

    fun onRememberMeChecked(isChecked: Boolean) {
        _loginUi = _loginUi.copy(isRememberMeChecked = isChecked)
    }

    private fun postRegularLoggingViewState() {
        _loginViewState.postValue(
            LoginViewState.RegularLogging
        )
    }

    private fun postFailedLoggingViewState() {
        _loginViewState.postValue(
            LoginViewState.FailedLogging
        )
    }

    private fun postNetworkErrorViewState() {
        _loginViewState.postValue(
            LoginViewState.NetworkError
        )
    }

    private fun postLoadingViewState() {
        _loginViewState.postValue(
            LoginViewState.Loading
        )
    }

}

@Suppress("UNCHECKED_CAST")
class LoginFragmentViewModelFactory(
    private val loginUseCase: LoginUseCase,
    private val getCachedLoginUseCase: GetCachedLoginUseCase,
    private val navigator: Navigator
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginFragmentViewModel(
            loginUseCase,
            getCachedLoginUseCase,
            navigator
        ) as T
    }

}

sealed class LoginViewState {
    object RegularLogging : LoginViewState()
    object FailedLogging : LoginViewState()
    object NetworkError : LoginViewState()
//    data class RegularLogging(val loginUi: LoginUi) : LoginViewState()
//    data class FailedLogging(val loginUi: LoginUi) : LoginViewState()
//    data class NetworkError(val loginUi: LoginUi) : LoginViewState()
    object Loading : LoginViewState()
}