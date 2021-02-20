package com.ssho.aeontest.domain

import com.ssho.aeontest.data.UserDataMappers
import com.ssho.aeontest.data.UserRepository
import com.ssho.aeontest.ui.model.LoginUi
import com.ssho.aeontest.utils.ResultWrapper

interface LoginUseCase {
    suspend operator fun invoke(loginUi: LoginUi): ResultWrapper<Unit>
}

class LoginUseCaseImpl(
    private val userRepository: UserRepository,
    private val userDataMappers: UserDataMappers
) : LoginUseCase {
    override suspend fun invoke(loginUi: LoginUi): ResultWrapper<Unit> {
        val userData = loginUi.let(userDataMappers.fromLoginUi)

        return userRepository.login(userData)
    }

}