package com.ssho.aeontest.data

import com.ssho.aeontest.data.model.LoginData

interface LoginRepository {
    var isLoginDataCached: Boolean
    fun getCachedLoginData(): LoginData
    fun cacheLoginData(loginData: LoginData)
    suspend fun getAccessToken(loginData: LoginData): String
}

class LoginRepositoryImpl(
    private val loginLocalDataSource: LoginLocalDataSource,
    private val loginRemoteDataSource: LoginRemoteDataSource
) : LoginRepository {

    override var isLoginDataCached: Boolean
        get() = loginLocalDataSource.isLoginDataCached
        set(boolean) { loginLocalDataSource.isLoginDataCached = boolean }

    override fun getCachedLoginData(): LoginData = loginLocalDataSource.getCachedLoginData()

    override fun cacheLoginData(loginData: LoginData) = loginLocalDataSource.cacheLoginData(loginData)

    override suspend fun getAccessToken(loginData: LoginData): String {
        return loginRemoteDataSource.fetchUserAccessToken(loginData)
    }


}