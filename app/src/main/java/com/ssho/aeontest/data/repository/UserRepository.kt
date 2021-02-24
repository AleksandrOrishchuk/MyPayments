package com.ssho.aeontest.data.repository

import com.ssho.aeontest.data.datasource.UserLocalDataSource
import com.ssho.aeontest.data.model.UserData

interface UserRepository {
    fun cacheUserAccessToken(accessToken: String)
    fun getCurrentUser(): UserData
    fun isUserLoggedIn(): Boolean
    fun confirmUserLogout()
    fun confirmUserLogin()
}

class UserRepositoryImpl(
    private val userLocalDataSource: UserLocalDataSource,
) : UserRepository {
    override fun cacheUserAccessToken(accessToken: String) {
        userLocalDataSource.user = userLocalDataSource.user.copy(accessToken = accessToken)
    }

    override fun getCurrentUser(): UserData {
        return userLocalDataSource.user
    }

    override fun isUserLoggedIn(): Boolean {
        return userLocalDataSource.isUserLoggedIn
    }

    override fun confirmUserLogout() {
        userLocalDataSource.isUserLoggedIn = false
    }

    override fun confirmUserLogin() {
        userLocalDataSource.isUserLoggedIn = true
    }

}