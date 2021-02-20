package com.ssho.aeontest.data

import com.ssho.aeontest.data.model.UserData
import com.ssho.aeontest.utils.ResultWrapper

interface UserRepository {
    suspend fun login(user: UserData): ResultWrapper<Unit>
    fun getCurrentUser(): UserData
    fun isUserLoggedIn(): Boolean
}

class UserRepositoryImpl(
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {
    override suspend fun login(user: UserData): ResultWrapper<Unit> {
        val userAccessToken = userRemoteDataSource.getUserAccessToken(user = user)

        return when(userAccessToken) {
            is ResultWrapper.Success -> {
                val updatedUser = user.copy(accessToken = userAccessToken.value)
                userLocalDataSource.user = updatedUser
                confirmUserLogin()
                ResultWrapper.Success(Unit)
            }
            ResultWrapper.InvalidLoginError -> ResultWrapper.InvalidLoginError
            ResultWrapper.NetworkError -> ResultWrapper.NetworkError
        }
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

    private fun confirmUserLogout() {
        userLocalDataSource.isUserLoggedIn = false
    }

}