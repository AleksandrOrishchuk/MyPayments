package com.ssho.aeontest.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.ssho.aeontest.data.model.AuthData

class AuthLocalDataSource(private val sharedPreferences: SharedPreferences) {
    companion object {
        private const val KEY_LOGIN = "key_email"
        private const val KEY_PASSWORD = "key_password"
        private const val KEY_IS_USER_REMEMBERED = "key_is_user_remembered"
    }

    var isAuthDataCached: Boolean
        get() = sharedPreferences.getBoolean(KEY_IS_USER_REMEMBERED, false)
        set(boolean) {
            sharedPreferences.edit { putBoolean(KEY_IS_USER_REMEMBERED, boolean) }
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

    fun getCachedAuthData(): AuthData = AuthData(
        login,
        password
    )

    fun cacheAuthData(authData: AuthData) {
        login = authData.login
        password = authData.password
    }
}