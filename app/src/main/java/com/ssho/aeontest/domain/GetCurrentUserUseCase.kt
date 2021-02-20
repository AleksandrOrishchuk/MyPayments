package com.ssho.aeontest.domain

import com.ssho.aeontest.data.UserRepository
import com.ssho.aeontest.data.model.UserData
import com.ssho.aeontest.utils.ResultWrapper

interface GetCurrentUserUseCase {
    operator fun invoke(): ResultWrapper<UserData>
}

class GetCurrentUserUseCaseImpl(
    private val userRepository: UserRepository
): GetCurrentUserUseCase {
    override fun invoke(): ResultWrapper<UserData> {
        return if (userRepository.isUserLoggedIn())
            ResultWrapper.Success(userRepository.getCurrentUser())
        else
            ResultWrapper.InvalidLoginError
    }
}