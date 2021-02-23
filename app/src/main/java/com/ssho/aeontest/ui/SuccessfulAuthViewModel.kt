package com.ssho.aeontest.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ssho.aeontest.Navigator
import com.ssho.aeontest.domain.usecase.UnauthorizeUserUseCase

abstract class SuccessfulAuthViewModel : ViewModel() {
    protected abstract val unauthorizeUser: UnauthorizeUserUseCase
    protected abstract val navigator: Navigator

    fun logout(): Boolean {
        unauthorizeUser()
        navigator.login()

        return true
    }
}