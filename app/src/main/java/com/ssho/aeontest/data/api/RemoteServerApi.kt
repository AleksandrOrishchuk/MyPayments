package com.ssho.aeontest.data.api

import okhttp3.ResponseBody
import retrofit2.http.*

interface RemoteServerApi {

    @FormUrlEncoded
    @Headers("app-key:12345", "v:1")
    @POST("login")
    suspend fun getAccessToken(
        @Field("login")
        login: String,
        @Field("password")
        password: String
    ): ResponseBody

    @Headers("app-key:12345", "v:1")
    @GET("payments?token={token}")
    suspend fun getPayments(
        @Path(value = "token", encoded = true)
        token: String
    ) //todo RECEIVE LIST OF PAYMENTS and wrap mapped result
}