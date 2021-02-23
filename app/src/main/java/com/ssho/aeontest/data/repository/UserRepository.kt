package com.ssho.aeontest.data.repository

import com.ssho.aeontest.data.datasource.UserLocalDataSource
import com.ssho.aeontest.data.model.UserData

interface UserRepository {
    fun setCurrentUser(user: UserData)
    fun getCurrentUser(): UserData
    fun isUserLoggedIn(): Boolean
    fun confirmUserLogout()
}

class UserRepositoryImpl(
    private val userLocalDataSource: UserLocalDataSource,
) : UserRepository {
    override fun setCurrentUser(user: UserData) {
        userLocalDataSource.user = user
        confirmUserLogin()
    }

    override fun getCurrentUser(): UserData {
        return userLocalDataSource.user
    }

    override fun isUserLoggedIn(): Boolean {
        return userLocalDataSource.isUserLoggedIn
    }

    private fun confirmUserLogin() {
        userLocalDataSource.isUserLoggedIn = true
    }

    override fun confirmUserLogout() {
        userLocalDataSource.isUserLoggedIn = false
    }

}