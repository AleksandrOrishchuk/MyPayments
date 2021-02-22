package com.ssho.aeontest.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ssho.aeontest.Navigator
import com.ssho.aeontest.domain.usecase.GetUserPaymentsUseCase
import com.ssho.aeontest.domain.usecase.UnauthorizeUserUseCase

class PaymentListFragmentViewModel(
    private val getUserPaymentsUseCase: GetUserPaymentsUseCase,
    override val unauthorizeUser: UnauthorizeUserUseCase,
    override val navigator: Navigator
) : SuccessfulAuthViewModel() {

}

@Suppress("UNCHECKED_CAST")
class PaymentListFragmentViewModelFactory(
    private val getUserPaymentsUseCase: GetUserPaymentsUseCase,
    private val unauthorizeUser: UnauthorizeUserUseCase,
    private val navigator: Navigator
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PaymentListFragmentViewModel(
            getUserPaymentsUseCase,
            unauthorizeUser,
            navigator
        ) as T
    }
}