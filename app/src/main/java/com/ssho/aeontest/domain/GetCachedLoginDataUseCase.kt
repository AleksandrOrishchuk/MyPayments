package com.ssho.aeontest.domain

import com.ssho.aeontest.data.UserDataMappers
import com.ssho.aeontest.data.UserRepository
import com.ssho.aeontest.ui.model.LoginUi

interface GetCachedLoginDataUseCase {
    operator fun invoke(): LoginUi
}

class GetCachedLoginDataUseCaseImpl(
    private val userRepository: UserRepository,
    private val userDataMappers: UserDataMappers
) : GetCachedLoginDataUseCase {
    override fun invoke(): LoginUi {
        return if (userRepository.isLoginDataCached())
            userRepository.getCurrentUser().let(userDataMappers.toLoginUi)
        else
            LoginUi()
    }
}