package com.ssho.aeontest.ui.auth_ui

import com.ssho.aeontest.data.model.AuthData
import com.ssho.aeontest.ui.model.AuthUiData

class AuthUiDataMapper: (AuthData) -> AuthUiData {
    override fun invoke(it: AuthData): AuthUiData =
        AuthUiData(
            it.login,
            it.password
        )
}