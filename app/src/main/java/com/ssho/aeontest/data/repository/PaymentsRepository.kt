package com.ssho.aeontest.data.repository

import com.ssho.aeontest.data.datasource.PaymentsRemoteDataSource
import com.ssho.aeontest.data.model.Payment

interface PaymentsRepository {
    suspend fun getPayments(accessToken: String): List<Payment>
}

class PaymentsRepositoryImpl(
    private val paymentsRemoteDataSource: PaymentsRemoteDataSource
) : PaymentsRepository {
    override suspend fun getPayments(accessToken: String): List<Payment> {
        return paymentsRemoteDataSource.getPayments(accessToken)
    }
}