package com.ssho.aeontest.ui

import android.text.format.DateFormat
import com.ssho.aeontest.data.model.Payment
import com.ssho.aeontest.ui.model.PaymentUi
import java.util.*

private const val DATE_FORMAT = "EEEE, MMM dd, yyyy / HH:mm"

class PaymentUiMapper : (Payment) -> PaymentUi {
    override fun invoke(it: Payment): PaymentUi {
        val title = it.title
        val amount = it.amount
        val currency = it.currency
        val date = Date(it.created)
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
}