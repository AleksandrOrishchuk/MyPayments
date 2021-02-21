package com.ssho.aeontest.usecase

import com.ssho.aeontest.data.LoginDataMappers
import com.ssho.aeontest.data.LoginRepository
import com.ssho.aeontest.ui.model.LoginUi

interface GetCachedLoginUseCase {
    operator fun invoke(): LoginUi
}

class GetCachedLoginUseCaseImpl(
    private val loginRepository: LoginRepository,
    private val loginDataMappers: LoginDataMappers
) : GetCachedLoginUseCase {
    override fun invoke(): LoginUi {
        return if (loginRepository.isLoginDataCached)
            getCachedLoginUi()
        else
            LoginUi()
    }

    private fun getCachedLoginUi(): LoginUi {
        val cachedLoginData = loginRepository.getCachedLoginData()
        val loginUi = cachedLoginData.let(loginDataMappers.toLoginUi)
        return loginUi.copy(
            isRememberMeChecked = true
        )
    }
}