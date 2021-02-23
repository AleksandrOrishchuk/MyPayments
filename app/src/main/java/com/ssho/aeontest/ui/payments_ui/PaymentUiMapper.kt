package com.ssho.aeontest.ui.payments_ui

import android.text.format.DateFormat
import com.ssho.aeontest.data.model.Payment
import com.ssho.aeontest.ui.model.PaymentUi
import java.util.*
import kotlin.math.floor
import kotlin.math.round

private const val DATE_FORMAT = "EEEE, MMM dd, yyyy / HH:mm"

class PaymentUiMapper : (Payment) -> PaymentUi {
    override fun invoke(it: Payment): PaymentUi {
        val title = it.title
        val amount = getUiFormattedAmount(it.amount)
        val currency = it.currency
        val date = Date(it.created * 1000)
        val dateFormatted = if (date == Date(0))
            ""
        else
            DateFormat.format(DATE_FORMAT, date).toString()

        return PaymentUi(
            title,
            amount,
            currency,
            dateFormatted
        )
    }

    private fun getUiFormattedAmount(amount: String): String {
        val numericAmount = amount.toDoubleOrNull() ?: return ""

        return if (floor(numericAmount) == numericAmount)
            numericAmount.toInt().toString()
        else
            String.format("%.2f", round(numericAmount * 100) / 100)
                .replace(',', '.')
    }
}