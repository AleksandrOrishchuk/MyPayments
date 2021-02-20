package com.ssho.aeontest.data

import com.ssho.aeontest.data.model.UserData
import com.ssho.aeontest.ui.model.LoginUi

class UserDataMappers {
    val toLoginUi = MapUserDataToLoginUi()
    val fromLoginUi = MapLoginUiToUserData()
}

class MapUserDataToLoginUi : (UserData) -> LoginUi {
    override fun invoke(userData: UserData): LoginUi {
        return LoginUi(
            login = userData.login,
            password = userData.password,
            isRememberMeChecked = userData.isRemembered
        )
    }
}

class MapLoginUiToUserData : (LoginUi) -> UserData {
    override fun invoke(loginUi: LoginUi): UserData {
        return UserData(
            login = loginUi.login,
            password = loginUi.password,
            isRemembered = loginUi.isRememberMeChecked
        )
    }

}