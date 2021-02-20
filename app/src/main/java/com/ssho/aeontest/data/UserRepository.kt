package com.ssho.aeontest.data

import android.content.SharedPreferences
import androidx.core.content.edit

class UserRepository(private val sharedPreferences: SharedPreferences) {

    companion object {
        private const val KEY_EMAIL = "key_email"
        private const val KEY_PASSWORD = "key_password"
        private const val KEY_TOKEN = "key_token"
    }

    var email: String
        get() = sharedPreferences.getString(KEY_EMAIL, "").orEmpty()
        set(value) {
            sharedPreferences.edit { putString(KEY_EMAIL, value) }
        }

    var password: String
        get() = sharedPreferences.getString(KEY_PASSWORD, "").orEmpty()
        set(value) {
            sharedPreferences.edit { putString(KEY_PASSWORD, value) }
        }

    var loginToken: String
        get() = sharedPreferences.getString(KEY_TOKEN, "").orEmpty()
        set(value) {
            sharedPreferences.edit { putString(KEY_TOKEN, value) }
        }
}