package com.ssho.aeontest.domain.usecase

import com.ssho.aeontest.data.UserRepository
import com.ssho.aeontest.data.model.UserData

interface GetCurrentUserUseCase {
    operator fun invoke(): UserData?
}

class GetCurrentUserUseCaseImpl(
    private val userRepository: UserRepository
): GetCurrentUserUseCase {
    override fun invoke(): UserData? {
        return if (userRepository.isUserLoggedIn())
            userRepository.getCurrentUser()
        else
            null
    }
}