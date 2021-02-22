package com.ssho.aeontest.domain.service

import com.ssho.aeontest.data.AuthRepository
import com.ssho.aeontest.data.model.AuthData
import com.ssho.aeontest.ui.model.AuthUiData

interface GetAuthUiDataService {
    operator fun invoke(): AuthUiData
}

class GetAuthUiDataServiceImpl(
    private val authRepository: AuthRepository,
) : GetAuthUiDataService {
    override fun invoke(): AuthUiData {
        val isLoginDataRemembered = authRepository.isAuthDataCached

        return if (isLoginDataRemembered)
            getCachedAuthUi()
        else
            AuthUiData()
    }

    private fun getCachedAuthUi(): AuthUiData {
        val cachedAuthData = authRepository.getCachedAuthData()
        val authUiData = mapAuthUiData(cachedAuthData)
        return authUiData.copy(
            isRememberMeChecked = true
        )
    }

    private fun mapAuthUiData(authData: AuthData): AuthUiData =
        AuthUiData(
            login = authData.login,
            password = authData.password
        )
}
