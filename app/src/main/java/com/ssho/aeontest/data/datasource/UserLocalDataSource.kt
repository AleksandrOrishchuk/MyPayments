package com.ssho.aeontest.data.datasource

import android.content.SharedPreferences
import androidx.core.content.edit
import com.ssho.aeontest.data.model.UserData

class UserLocalDataSource(private val sharedPreferences: SharedPreferences) {

    companion object {
        private const val KEY_IS_USER_LOGGED_IN = "key_is_user_logged_in"
        private const val KEY_TOKEN = "key_token"
    }

    var user: UserData
        get() = UserData(
            accessToken = accessToken
        )
        set(userData) {
            accessToken = userData.accessToken
        }

    var isUserLoggedIn: Boolean
        get() = sharedPreferences.getBoolean(KEY_IS_USER_LOGGED_IN, false)
        set(value) {
            sharedPreferences.edit { putBoolean(KEY_IS_USER_LOGGED_IN, value) }
        }

    private var accessToken: String
        get() = sharedPreferences.getString(KEY_TOKEN, "").orEmpty()
        set(value) {
            sharedPreferences.edit { putString(KEY_TOKEN, value) }
        }
}