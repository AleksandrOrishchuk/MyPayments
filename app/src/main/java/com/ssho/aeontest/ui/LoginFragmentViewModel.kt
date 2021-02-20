package com.ssho.aeontest.ui

import androidx.lifecycle.ViewModel

class LoginFragmentViewModel : ViewModel() {
}

sealed class LoginViewState {
    object RegularLogging: LoginViewState()
    object FailedLogging: LoginViewState()
}