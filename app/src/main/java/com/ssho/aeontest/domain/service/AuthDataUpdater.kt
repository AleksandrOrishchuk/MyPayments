package com.ssho.aeontest.domain.service

import com.ssho.aeontest.data.AuthDataMapper
import com.ssho.aeontest.data.AuthRepository
import com.ssho.aeontest.ui.model.AuthUiData

interface AuthDataUpdater {
    operator fun invoke(authUiData: AuthUiData)
}

class AuthDataUpdaterImpl(
    private val authRepository: AuthRepository,
    private val authDataMapper: AuthDataMapper
) : AuthDataUpdater {
    override fun invoke(authUiData: AuthUiData) {
        if (authUiData.isRememberMeChecked)
            cacheAuthData(authUiData)
        else
            authRepository.isAuthDataCached = false

    }

    private fun cacheAuthData(authUiData: AuthUiData) {
        val authData = authUiData.let(authDataMapper)
        authRepository.cacheAuthData(authData)
        authRepository.isAuthDataCached = true
    }
}