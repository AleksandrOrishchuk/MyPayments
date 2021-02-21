package com.ssho.aeontest.data

import com.ssho.aeontest.data.api.RemoteServerApi
import com.ssho.aeontest.data.model.LoginData
import org.json.JSONObject
import java.lang.RuntimeException

interface LoginRemoteDataSource {
    suspend fun fetchUserAccessToken(loginData: LoginData): String
}

class LoginRemoteDataSourceImpl(
    private val remoteServerApi: RemoteServerApi
) : LoginRemoteDataSource {
    override suspend fun fetchUserAccessToken(loginData: LoginData): String {
        val responseBody = remoteServerApi.getAccessToken(loginData.login, loginData.password)

        val jsonString = responseBody.string()
        val json = JSONObject(jsonString)
        val isLoginSuccessful = json.getBoolean("success")

        return if (isLoginSuccessful)
            getAccessToken(json)
        else {
            val error = json.getJSONObject("error")
            val code = error.getInt("error_code")
            val message = error.getString("error_msg")
            throw LoginFailureException(code, message)
        }
    }

    private fun getAccessToken(json: JSONObject): String {
        val response = json.getJSONObject("response")

        return response.getString("token")
    }
}

class LoginFailureException(val code: Int, override val message: String) : RuntimeException()