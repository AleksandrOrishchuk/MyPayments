package com.ssho.aeontest.data.model

data class UserData(
    val login: String = "",
    val password: String = "",
    val accessToken: String = "",
    val isRemembered: Boolean = false
)