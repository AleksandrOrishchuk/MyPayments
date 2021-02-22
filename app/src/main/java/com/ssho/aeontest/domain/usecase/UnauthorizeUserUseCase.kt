package com.ssho.aeontest.domain.usecase

import com.ssho.aeontest.data.UserRepository

interface UnauthorizeUserUseCase {
    operator fun invoke()
}

class UnauthorizeUserUseCaseImpl(
    private val userRepository: UserRepository
) : UnauthorizeUserUseCase {
    override fun invoke() {
        userRepository.confirmUserLogout()
    }
}