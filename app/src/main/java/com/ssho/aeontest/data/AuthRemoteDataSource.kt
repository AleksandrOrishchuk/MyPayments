package com.ssho.aeontest.data

import com.ssho.aeontest.data.api.RemoteServerApi
import com.ssho.aeontest.data.model.AuthData
import okhttp3.ResponseBody
import org.json.JSONObject
import java.lang.RuntimeException

interface AuthRemoteDataSource {
    suspend fun fetchUserAccessToken(authData: AuthData): String
}

class AuthRemoteDataSourceImpl(
    private val remoteServerApi: RemoteServerApi
) : AuthRemoteDataSource {
    override suspend fun fetchUserAccessToken(authData: AuthData): String {
        val apiResponseBody = remoteServerApi.getAccessToken(authData.login, authData.password)

        return parseTokenApiResponse(apiResponseBody)
    }

    private fun parseTokenApiResponse(responseBody: ResponseBody): String {
        val jsonString = responseBody.string()
        val json = JSONObject(jsonString)
        val isAuthorisationSuccessful = json.getBoolean("success")

        return if (isAuthorisationSuccessful)
            getAccessToken(json)
        else {
            val error = json.getJSONObject("error")
            val code = error.getInt("error_code")
            val message = error.getString("error_msg")

            throw AuthorizationError(code, message)
        }
    }

    private fun getAccessToken(json: JSONObject): String {
        val response = json.getJSONObject("response")

        return response.getString("token")
    }

}

class AuthorizationError(val code: Int, override val message: String) : RuntimeException()