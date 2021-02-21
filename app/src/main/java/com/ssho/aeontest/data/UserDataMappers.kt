package com.ssho.aeontest.data

import com.ssho.aeontest.data.model.LoginData
import com.ssho.aeontest.data.model.UserData
import com.ssho.aeontest.ui.model.LoginUi

class UserDataMappers {
    val toLoginData = MapUserDataToLoginData()
    val fromLoginData = MapLoginDataToUserData()
}

class MapUserDataToLoginData : (UserData) -> LoginData {
    override fun invoke(userData: UserData): LoginData {
        return LoginData(
            login = userData.login,
            password = userData.password,
        )
    }
}

class MapLoginDataToUserData : (LoginData) -> UserData {
    override fun invoke(loginData: LoginData): UserData {
        return UserData(
            login = loginData.login,
            password = loginData.password,
        )
    }

}