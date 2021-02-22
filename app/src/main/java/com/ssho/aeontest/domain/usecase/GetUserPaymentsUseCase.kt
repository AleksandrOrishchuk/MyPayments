package com.ssho.aeontest.domain.usecase

import com.ssho.aeontest.data.AuthorizationError
import com.ssho.aeontest.data.model.Payment
import com.ssho.aeontest.data.repository.PaymentsRepository

interface GetUserPaymentsUseCase {
    suspend operator fun invoke(): List<Payment>
}

class GetUserPaymentsUseCaseImpl(
    private val getCurrentUser: GetCurrentUserUseCase,
    private val paymentsRepository: PaymentsRepository
) : GetUserPaymentsUseCase {
    override suspend fun invoke(): List<Payment> {
        val accessToken = getCurrentUser()?.accessToken
            ?: throw AuthorizationError(666, "If you see this, auth logic is broken")
        return paymentsRepository.getPayments(accessToken)
    }
}