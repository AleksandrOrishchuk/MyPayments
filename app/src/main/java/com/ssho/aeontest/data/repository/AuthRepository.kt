package com.ssho.aeontest.data.repository

import com.ssho.aeontest.data.datasource.AuthLocalDataSource
import com.ssho.aeontest.data.datasource.AuthRemoteDataSource
import com.ssho.aeontest.data.model.AuthData

interface AuthRepository {
    fun getCachedAuthData(): AuthData
    fun cacheAuthData(authData: AuthData)
    suspend fun getAccessToken(authData: AuthData): String
}

class AuthRepositoryImpl(
    private val authLocalDataSource: AuthLocalDataSource,
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepository {

    override fun getCachedAuthData(): AuthData = authLocalDataSource.getCachedAuthData()

    override fun cacheAuthData(authData: AuthData) = authLocalDataSource.cacheAuthData(authData)

    override suspend fun getAccessToken(authData: AuthData): String {
        return authRemoteDataSource.fetchUserAccessToken(authData)
    }

}