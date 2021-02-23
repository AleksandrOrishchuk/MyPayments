package com.ssho.aeontest.domain.service

import com.ssho.aeontest.data.model.AuthData
import com.ssho.aeontest.data.repository.AuthRepository

interface AuthDataCacheManager {
    fun getAuthData(): AuthData
    fun cacheAuthData(authData: AuthData)
}

class AuthDataCacheManagerImpl(
    private val authRepository: AuthRepository
) : AuthDataCacheManager {
    override fun getAuthData(): AuthData {
        return authRepository.getCachedAuthData()
    }

    override fun cacheAuthData(authData: AuthData) {
        return authRepository.cacheAuthData(authData)
    }
}

