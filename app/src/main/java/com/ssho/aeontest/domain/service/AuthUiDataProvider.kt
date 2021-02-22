package com.ssho.aeontest.domain.service

import com.ssho.aeontest.data.AuthRepository
import com.ssho.aeontest.ui.auth_ui.AuthUiDataMapper
import com.ssho.aeontest.ui.model.AuthUiData

interface AuthUiDataProvider {
    operator fun invoke(): AuthUiData
}

class AuthUiDataProviderImpl(
    private val authRepository: AuthRepository,
    private val authUiDataMapper: AuthUiDataMapper
) : AuthUiDataProvider {
    override fun invoke(): AuthUiData {
        val isAuthDataRemembered = authRepository.isAuthDataCached

        return if (isAuthDataRemembered)
            getCachedAuthUiData()
        else
            AuthUiData()
    }

    private fun getCachedAuthUiData(): AuthUiData {
        val cachedAuthData = authRepository.getCachedAuthData()
        val authUiData = cachedAuthData.let(authUiDataMapper)
        return authUiData.copy(
            isRememberMeChecked = true
        )
    }
}
