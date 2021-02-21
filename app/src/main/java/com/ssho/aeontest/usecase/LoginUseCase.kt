package com.ssho.aeontest.usecase

import com.ssho.aeontest.data.LoginDataMappers
import com.ssho.aeontest.data.LoginRepository
import com.ssho.aeontest.data.UserDataMappers
import com.ssho.aeontest.data.UserRepository
import com.ssho.aeontest.data.model.LoginData
import com.ssho.aeontest.ui.model.LoginUi

interface LoginUseCase {
    suspend operator fun invoke(loginUi: LoginUi)
}

class LoginUseCaseImpl(
    private val loginRepository: LoginRepository,
    private val loginDataMappers: LoginDataMappers,
    private val userRepository: UserRepository,
    private val userDataMappers: UserDataMappers,
) : LoginUseCase {
    override suspend fun invoke(loginUi: LoginUi) {
        val loginData = loginUi.let(loginDataMappers.fromLoginUi)

        if (loginUi.isRememberMeChecked)
            cacheLoginData(loginData)
        else
            loginRepository.isLoginDataCached = false

        val userAccessToken = loginRepository.getAccessToken(loginData)
        var user = loginData.let(userDataMappers.fromLoginData)
        user = user.copy(accessToken = userAccessToken)

        userRepository.setCurrentUser(user)
    }

    private fun cacheLoginData(loginData: LoginData) {
        loginRepository.cacheLoginData(loginData)
        loginRepository.isLoginDataCached = true
    }
}