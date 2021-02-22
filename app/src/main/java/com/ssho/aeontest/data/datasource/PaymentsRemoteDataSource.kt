package com.ssho.aeontest.data.datasource

import com.ssho.aeontest.data.AuthorizationError
import com.ssho.aeontest.data.api.RemoteServerApi
import com.ssho.aeontest.data.model.Payment
import okhttp3.ResponseBody
import org.json.JSONObject

class PaymentsRemoteDataSource(
    private val remoteServerApi: RemoteServerApi
) {
    suspend fun getPayments(accessToken: String): List<Payment> {
        val apiResponseBody = remoteServerApi.getPayments(accessToken)

        return parsePaymentsApiResponse(apiResponseBody)
    }

    private fun parsePaymentsApiResponse(responseBody: ResponseBody): List<Payment> {
        val jsonString = responseBody.string()
        val json = JSONObject(jsonString)
        val isAuthorisationSuccessful = json.getBoolean("success")

        return if (isAuthorisationSuccessful)
            getPaymentList(json)
        else {
            val error = json.getJSONObject("error")
            val code = error.getInt("error_code")
            val message = error.getString("error_msg")

            throw AuthorizationError(code, message)
        }
    }

    private fun getPaymentList(json: JSONObject): List<Payment> {
        val jsonArray = json.getJSONArray("response")
        val payments = mutableListOf<Payment>()

        for (i in 0 until jsonArray.length()) {
            with(jsonArray.getJSONObject(i)) {
                val title = optString("desc")
                val amount = optString("amount")
                val currency = optString("currency")
                val created = optLong("created", Long.MIN_VALUE)
                payments.add(
                    Payment(title, amount, currency, created)
                )
            }
        }

        return payments
    }

}