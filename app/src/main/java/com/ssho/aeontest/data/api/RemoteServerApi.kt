package com.ssho.aeontest.data.api

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RemoteServerApi {

    @POST("/login?login={login},password={password}")
    suspend fun loginUser(
        @Path(value = "login", encoded = true)
        login: String,
        @Path(value = "password", encoded = true)
        password: String
    ) //todo RECEIVE TOKEN FORMAT and parse into wrapped result

    @GET("/payments?token={token}")
    suspend fun getPayments(
        @Path(value = "token", encoded = true)
        token: String
    ) //todo RECEIVE LIST OF PAYMENTS and wrap mapped result
}