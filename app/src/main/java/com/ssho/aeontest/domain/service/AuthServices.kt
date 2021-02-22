package com.ssho.aeontest.domain.service

import com.ssho.aeontest.data.model.AuthData
import com.ssho.aeontest.ui.model.AuthUiData

interface AuthServices {
    fun getAuthUiData(): AuthUiData

}

class AuthServicesImpl(
    private val getAuthUiDataService: GetAuthUiDataService
): AuthServices {
    override fun getAuthUiData(): AuthUiData {
        return getAuthUiDataService()
    }
}

