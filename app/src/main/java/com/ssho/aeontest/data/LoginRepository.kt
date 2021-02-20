package com.ssho.aeontest.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.ssho.aeontest.data.model.LoginData

interface LoginRepository {
    var isLoginDataCached: Boolean
    fun getCachedLoginData(): LoginData
    fun cacheLoginData(loginData: LoginData)
}

class LoginRepositoryImpl(
    private val sharedPreferences: SharedPreferences
) : LoginRepository {
    companion object {
        private const val KEY_LOGIN = "key_email"
        private const val KEY_PASSWORD = "key_password"
        private const val KEY_IS_USER_REMEMBERED = "key_is_user_remembered"
    }

    override var isLoginDataCached: Boolean
        get() = sharedPreferences.getBoolean(KEY_IS_USER_REMEMBERED, false)
        set(value) {
            sharedPreferences.edit { putBoolean(KEY_IS_USER_REMEMBERED, value) }
        }

    override fun getCachedLoginData(): LoginData {
        return LoginData(
            login,
            password
        )
    }

    override fun cacheLoginData(loginData: LoginData) {
        login = loginData.login
        password = loginData.password
    }

    private var login: String
        get() = sharedPreferences.getString(KEY_LOGIN, "").orEmpty()
        set(value) {
            sharedPreferences.edit { putString(KEY_LOGIN, value) }
        }

    private var password: String
        get() = sharedPreferences.getString(KEY_PASSWORD, "").orEmpty()
        set(value) {
            sharedPreferences.edit { putString(KEY_PASSWORD, value) }
        }
}