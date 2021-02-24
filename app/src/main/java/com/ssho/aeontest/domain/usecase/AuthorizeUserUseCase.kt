package com.ssho.aeontest.domain.usecase

import com.ssho.aeontest.data.repository.AuthRepository
import com.ssho.aeontest.data.repository.UserRepository
import com.ssho.aeontest.data.model.AuthData
import com.ssho.aeontest.data.model.UserData

interface AuthorizeUserUseCase {
    suspend operator fun invoke(authData: AuthData)
}

class AuthorizeUserUseCaseImpl(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : AuthorizeUserUseCase {
    override suspend fun invoke(authData: AuthData) {
        val userAccessToken = authRepository.getAccessToken(authData)
        userRepository.cacheUserAccessToken(userAccessToken)
        userRepository.confirmUserLogin()
    }
}