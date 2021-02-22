package com.ssho.aeontest.data

import com.ssho.aeontest.data.model.AuthData
import com.ssho.aeontest.ui.model.AuthUiData

class AuthDataMapper : (AuthUiData) -> AuthData {
    override fun invoke(it: AuthUiData): AuthData =
        AuthData(
            it.login,
            it.password
        )
}