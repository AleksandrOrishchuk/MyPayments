package com.ssho.aeontest.ui

import com.ssho.aeontest.Navigator
import com.ssho.aeontest.domain.usecase.GetUserPaymentsUseCase
import com.ssho.aeontest.domain.usecase.UnauthorizeUserUseCase

class PaymentListFragmentViewModel(
    private val getUserPaymentsUseCase: GetUserPaymentsUseCase,
    override val unauthorizeUser: UnauthorizeUserUseCase,
    override val navigator: Navigator
) : SuccessfulAuthViewModel() {

}