package com.ssho.aeontest.domain

import com.ssho.aeontest.data.LoginDataMappers
import com.ssho.aeontest.data.LoginRepository
import com.ssho.aeontest.data.UserDataMappers
import com.ssho.aeontest.data.UserRepository
import com.ssho.aeontest.ui.model.LoginUi

interface GetCachedLoginDataUseCase {
    operator fun invoke(): LoginUi
}

class GetCachedLoginDataUseCaseImpl(
    private val loginRepository: LoginRepository,
    private val loginDataMappers: LoginDataMappers
) : GetCachedLoginDataUseCase {
    override fun invoke(): LoginUi {
        return if (loginRepository.isLoginDataCached) {
            val loginData = loginRepository.getCachedLoginData()
            val loginUi = loginData.let(loginDataMappers.toLoginUi)
            loginUi.copy(
                isRememberMeChecked = loginRepository.isLoginDataCached
            )
        }
        else
            LoginUi()
    }
}