package com.ssho.aeontest.usecase

import com.ssho.aeontest.data.LoginFailureException
import com.ssho.aeontest.data.UserRepository
import com.ssho.aeontest.data.model.UserData

interface GetCurrentUserUseCase {
    operator fun invoke(): UserData
}

class GetCurrentUserUseCaseImpl(
    private val userRepository: UserRepository
): GetCurrentUserUseCase {
    override fun invoke(): UserData {
        return if (userRepository.isUserLoggedIn())
            userRepository.getCurrentUser()
        else
            throw LoginFailureException(404, "User is logged out.")
    }
}