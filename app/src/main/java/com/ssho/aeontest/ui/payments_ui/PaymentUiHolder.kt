package com.ssho.aeontest.ui.payments_ui

import androidx.recyclerview.widget.RecyclerView
import com.ssho.aeontest.R
import com.ssho.aeontest.databinding.ListItemPaymentBinding
import com.ssho.aeontest.ui.model.PaymentUi

class PaymentUiHolder(
    private val viewBinding: ListItemPaymentBinding
) : RecyclerView.ViewHolder(viewBinding.root) {
    fun bind(paymentUi: PaymentUi) {
        viewBinding.apply {
            with(paymentUi) {
                if (title.isBlank())
                    titleTextView.setText(R.string.payment_title_empty_text)
                else
                    titleTextView.text = title

                if (amount.isBlank())
                    amountTextView.setText(R.string.amount_title_empty_text)
                else
                    amountTextView.text = amount

                if (currency.isBlank())
                    currencyTextView.setText(R.string.payment_currency_empty_text)
                else
                    currencyTextView.text = currency

                if (paymentDate.isBlank())
                    paymentDateTextView.setText(R.string.payment_date_empty_text)
                else
                    paymentDateTextView.text = paymentDate
            }
        }
    }
}