package com.ssho.aeontest.domain.service

import com.ssho.aeontest.ui.model.AuthUiData

interface AuthDataCacheManager {
    fun getAuthUiData(): AuthUiData
    fun rememberOrClearCachedAuthData(authUiData: AuthUiData)
}

class AuthDataCacheManagerImpl(
    private val authUiDataProvider: AuthUiDataProvider,
    private val authDataUpdater: AuthDataUpdater
): AuthDataCacheManager {
    override fun getAuthUiData(): AuthUiData {
        return authUiDataProvider()
    }

    override fun rememberOrClearCachedAuthData(authUiData: AuthUiData) {
        return authDataUpdater(authUiData)
    }
}

