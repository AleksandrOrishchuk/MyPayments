package com.ssho.aeontest.data

import com.ssho.aeontest.data.model.UserData
import com.ssho.aeontest.utils.ResultWrapper

interface UserRepository {
    suspend fun login(userData: UserData): ResultWrapper<Unit>
    fun getCurrentUser(): UserData
    fun isLoginDataCached(): Boolean
    fun isUserLoggedIn(): Boolean
    fun confirmUserLogin()
    fun confirmUserLogout()
}

class UserRepositoryImpl(
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {
    override suspend fun login(user: UserData): ResultWrapper<Unit> {
        if (user.isRemembered)
            userLocalDataSource.user = user

        val userAccessToken = userRemoteDataSource.getUserAccessToken(user = user)

        return when(userAccessToken) {
            is ResultWrapper.Success -> {
                val updatedUser = user.copy(accessToken = userAccessToken.value)
                userLocalDataSource.user = updatedUser
                ResultWrapper.Success(Unit)
            }
            ResultWrapper.InvalidLoginError -> ResultWrapper.InvalidLoginError
            ResultWrapper.NetworkError -> ResultWrapper.NetworkError
        }
    }

    override fun getCurrentUser(): UserData {
        return userLocalDataSource.user
    }

    override fun isLoginDataCached(): Boolean {
        return userLocalDataSource.user.isRemembered
    }

    override fun isUserLoggedIn(): Boolean {
        return userLocalDataSource.isUserLoggedIn
    }

    override fun confirmUserLogin() {
        userLocalDataSource.isUserLoggedIn = true
    }

    override fun confirmUserLogout() {
        userLocalDataSource.isUserLoggedIn = false
    }

}