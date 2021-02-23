package com.ssho.aeontest.data.api

import okhttp3.ResponseBody
import retrofit2.http.*

interface RemoteServerApi {
    companion object {
        private const val APP_KEY_HEADER = "app-key:12345"
        private const val VERSION_HEADER = "v:1"
    }

    @FormUrlEncoded
    @Headers(APP_KEY_HEADER, VERSION_HEADER)
    @POST("login")
    suspend fun getAccessToken(
        @Field("login")
        login: String,
        @Field("password")
        password: String
    ): ResponseBody

    @Headers(APP_KEY_HEADER, VERSION_HEADER)
    @GET("payments")
    suspend fun getPayments(
        @Query("token")
        token: String
    ): ResponseBody
}