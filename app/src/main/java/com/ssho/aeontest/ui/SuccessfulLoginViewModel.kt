package com.ssho.aeontest.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ssho.aeontest.Navigator
import com.ssho.aeontest.usecase.LogoutUseCase

class SuccessfulLoginViewModel(
    private val logoutUseCase: LogoutUseCase,
    private val navigator: Navigator
) : ViewModel() {

    fun logout() {
        logoutUseCase()
        navigator.login()
    }
}

@Suppress("UNCHECKED_CAST")
class SuccessfulLoginViewModelFactory(
    private val logoutUseCase: LogoutUseCase,
    private val navigator: Navigator
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SuccessfulLoginViewModel(
            logoutUseCase,
            navigator
        ) as T
    }
}