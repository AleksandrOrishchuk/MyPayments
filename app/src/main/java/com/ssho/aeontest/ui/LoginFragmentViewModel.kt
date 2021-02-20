package com.ssho.aeontest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ssho.aeontest.domain.GetCachedLoginUseCase
import com.ssho.aeontest.domain.LoginUseCase
import com.ssho.aeontest.ui.model.LoginUi

class LoginFragmentViewModel(
    private val loginUseCase: LoginUseCase,
    private val getCachedLogin: GetCachedLoginUseCase,
) : ViewModel() {
    val loginViewState: LiveData<LoginViewState> get() = _loginViewState
    val successfulLogin: LiveData<Unit> get() = _successfulLogin
    private val _loginViewState: MutableLiveData<LoginViewState> =
        MutableLiveData(LoginViewState.Loading)
    private val _successfulLogin = MutableLiveData<Unit>()

init {
    val initialLoginUi = getCachedLogin()
    postRegularLoggingViewState(initialLoginUi)
}

    private fun postRegularLoggingViewState(loginUi: LoginUi) {
        _loginViewState.postValue(
            LoginViewState.RegularLogging(
                loginUi
            )
        )
    }

    private fun postFailedLoggingViewState(loginUi: LoginUi) {
        _loginViewState.postValue(
            LoginViewState.FailedLogging(
                loginUi
            )
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
    private val getCachedLoginUseCase: GetCachedLoginUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginFragmentViewModel(
            loginUseCase,
            getCachedLoginUseCase
        ) as T
    }

}

sealed class LoginViewState {
    data class RegularLogging(val loginUi: LoginUi) : LoginViewState()
    data class FailedLogging(val loginUi: LoginUi) : LoginViewState()
    object Loading : LoginViewState()
}