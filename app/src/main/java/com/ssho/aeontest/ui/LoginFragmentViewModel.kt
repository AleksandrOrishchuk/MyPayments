package com.ssho.aeontest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginFragmentViewModel : ViewModel() {
    val loginViewState: LiveData<LoginViewState> get() = _loginViewState
    private val _loginViewState: MutableLiveData<LoginViewState> =
        MutableLiveData(LoginViewState.Loading)

}

sealed class LoginViewState {
    object RegularLogging : LoginViewState()
    object FailedLogging : LoginViewState()
    object Loading : LoginViewState()
}