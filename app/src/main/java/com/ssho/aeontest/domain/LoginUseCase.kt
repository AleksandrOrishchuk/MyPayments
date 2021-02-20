package com.ssho.aeontest.domain

import com.ssho.aeontest.data.LoginDataMappers
import com.ssho.aeontest.data.LoginRepository
import com.ssho.aeontest.data.UserDataMappers
import com.ssho.aeontest.data.UserRepository
import com.ssho.aeontest.ui.model.LoginUi
import com.ssho.aeontest.utils.ResultWrapper

interface LoginUseCase {
    suspend operator fun invoke(loginUi: LoginUi): ResultWrapper<Unit>
}

class LoginUseCaseImpl(
    private val loginRepository: LoginRepository,
    private val loginDataMappers: LoginDataMappers,
    private val userRepository: UserRepository,
    private val userDataMappers: UserDataMappers,
) : LoginUseCase {
    override suspend fun invoke(loginUi: LoginUi): ResultWrapper<Unit> {
        if (loginUi.isRememberMeChecked)
            cacheLoginData(loginUi)
        else
            loginRepository.isLoginDataCached = false

        val userData = loginUi.let(userDataMappers.fromLoginUi)

        return userRepository.login(userData)
    }

    private fun cacheLoginData(loginUi: LoginUi) {
        val loginData = loginUi.let(loginDataMappers.fromLoginUi)
        loginRepository.cacheLoginData(loginData)
        loginRepository.isLoginDataCached = true
    }
}