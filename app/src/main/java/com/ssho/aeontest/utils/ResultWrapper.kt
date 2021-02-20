package com.ssho.aeontest.utils

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    object NetworkError: ResultWrapper<Nothing>()
    object InvalidLoginError: ResultWrapper<Nothing>()
}