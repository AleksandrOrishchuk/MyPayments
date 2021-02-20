package com.ssho.aeontest.data

import com.ssho.aeontest.data.model.LoginData
import com.ssho.aeontest.ui.model.LoginUi

class LoginDataMappers {
    val fromLoginUi = MapLoginUiToLoginData()
    val toLoginUi = MapLoginDataToLoginUi()
}

class MapLoginUiToLoginData : (LoginUi) -> LoginData {
    override fun invoke(loginUi: LoginUi): LoginData {
        return LoginData(
            login = loginUi.login,
            password = loginUi.password
        )
    }
}

class MapLoginDataToLoginUi : (LoginData) -> LoginUi {
    override fun invoke(loginData: LoginData): LoginUi {
        return LoginUi(
            login = loginData.login,
            password = loginData.password
        )
    }

}