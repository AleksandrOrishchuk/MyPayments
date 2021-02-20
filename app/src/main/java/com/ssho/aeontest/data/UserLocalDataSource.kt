package com.ssho.aeontest.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.ssho.aeontest.data.model.UserData

class UserLocalDataSource(private val sharedPreferences: SharedPreferences) {

    companion object {
        private const val KEY_IS_USER_LOGGED_IN = "key_is_user_logged_in"
        private const val KEY_LOGIN = "key_email"
        private const val KEY_PASSWORD = "key_password"
        private const val KEY_TOKEN = "key_token"
        private const val KEY_IS_USER_REMEMBERED = "key_is_user_remembered"
    }

    var user: UserData
        get() = UserData(
            login = login,
            password = password,
            accessToken = accessToken,
            isRemembered = isRemembered
        )
        set(userData) {
            login = userData.login
            password = userData.password
            accessToken = userData.accessToken
            isRemembered = userData.isRemembered
        }

    var isUserLoggedIn: Boolean
        get() = sharedPreferences.getBoolean(KEY_IS_USER_LOGGED_IN, false)
        set(value) {
            sharedPreferences.edit { putBoolean(KEY_IS_USER_LOGGED_IN, value) }
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

    private var accessToken: String
        get() = sharedPreferences.getString(KEY_TOKEN, "").orEmpty()
        set(value) {
            sharedPreferences.edit { putString(KEY_TOKEN, value) }
        }

    private var isRemembered: Boolean
        get() = sharedPreferences.getBoolean(KEY_IS_USER_REMEMBERED, false)
        set(value) {
            sharedPreferences.edit { putBoolean(KEY_IS_USER_REMEMBERED, value) }
        }
}