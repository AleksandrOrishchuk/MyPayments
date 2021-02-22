package com.ssho.aeontest.domain.usecase

import com.ssho.aeontest.data.AuthRepository
import com.ssho.aeontest.data.UserRepository
import com.ssho.aeontest.data.model.AuthData
import com.ssho.aeontest.data.model.UserData
import com.ssho.aeontest.ui.model.AuthUiData

interface AuthorizeUserUseCase {
    suspend operator fun invoke(authUiData: AuthUiData)
}

class AuthorizeUserUseCaseImpl(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
) : AuthorizeUserUseCase {
    override suspend fun invoke(authUiData: AuthUiData) {
        val authData = mapAuthData(authUiData)

        if (authUiData.isRememberMeChecked)
            cacheAuthData(authData)
        else
            authRepository.isAuthDataCached = false

        val userAccessToken = authRepository.getAccessToken(authData)
        val user = UserData(accessToken = userAccessToken)

        userRepository.setCurrentUser(user)
    }

    private fun mapAuthData(authUiData: AuthUiData): AuthData =
        AuthData(
            login = authUiData.login,
            password = authUiData.password
        )

    private fun cacheAuthData(authData: AuthData) {
        authRepository.cacheAuthData(authData)
        authRepository.isAuthDataCached = true
    }
}