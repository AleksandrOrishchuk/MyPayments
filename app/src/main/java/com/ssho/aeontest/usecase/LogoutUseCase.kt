package com.ssho.aeontest.usecase

import com.ssho.aeontest.data.UserRepository
import com.ssho.aeontest.ui.model.LoginUi

interface LogoutUseCase {
    operator fun invoke()
}

class LogoutUseCaseImpl(
    private val userRepository: UserRepository
) : LogoutUseCase {
    override fun invoke() {
        userRepository.confirmUserLogout()
    }
}