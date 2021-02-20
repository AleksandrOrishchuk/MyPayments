package com.ssho.aeontest.data

import com.ssho.aeontest.data.api.RemoteServerApi
import com.ssho.aeontest.data.model.UserData
import com.ssho.aeontest.utils.ResultWrapper

interface UserRemoteDataSource {
    suspend fun getUserAccessToken(user: UserData): ResultWrapper<String>
}

class UserRemoteDataSourceImpl(
    private val remoteServerApi: RemoteServerApi
) : UserRemoteDataSource {
    override suspend fun getUserAccessToken(user: UserData): ResultWrapper<String> {
        TODO("Not yet implemented")
    }
}