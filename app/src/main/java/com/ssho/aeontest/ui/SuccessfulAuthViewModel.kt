package com.ssho.aeontest.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ssho.aeontest.Navigator
import com.ssho.aeontest.domain.usecase.UnauthorizeUserUseCase

abstract class SuccessfulAuthViewModel() : ViewModel() {
    abstract val unauthorizeUser: UnauthorizeUserUseCase
    abstract val navigator: Navigator

    fun logout(): Boolean {
        unauthorizeUser()
        navigator.login()

        return true
    }
}

//@Suppress("UNCHECKED_CAST")
//class SuccessfulLoginViewModelFactory(
//    private val unauthorizeUserUseCase: UnauthorizeUserUseCase,
//    private val navigator: Navigator
//) : ViewModelProvider.Factory {
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        return SuccessfulAuthViewModel(
//            unauthorizeUserUseCase,
//            navigator
//        ) as T
//    }
//}