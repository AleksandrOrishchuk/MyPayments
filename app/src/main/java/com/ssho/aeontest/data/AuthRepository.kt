package com.ssho.aeontest.data

import com.ssho.aeontest.data.model.AuthData

interface AuthRepository {
    var isAuthDataCached: Boolean
    fun getCachedAuthData(): AuthData
    fun cacheAuthData(authData: AuthData)
    suspend fun getAccessToken(authData: AuthData): String
}

class AuthRepositoryImpl(
    private val authLocalDataSource: AuthLocalDataSource,
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepository {

    override var isAuthDataCached: Boolean
        get() = authLocalDataSource.isAuthDataCached
        set(boolean) { authLocalDataSource.isAuthDataCached = boolean }

    override fun getCachedAuthData(): AuthData = authLocalDataSource.getCachedAuthData()

    override fun cacheAuthData(authData: AuthData) = authLocalDataSource.cacheAuthData(authData)

    override suspend fun getAccessToken(authData: AuthData): String {
        return authRemoteDataSource.fetchUserAccessToken(authData)
    }


}