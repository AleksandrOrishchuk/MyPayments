package com.ssho.aeontest.ui.payments_ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.ssho.aeontest.databinding.ListItemPaymentBinding
import com.ssho.aeontest.ui.model.PaymentUi

class PaymentUiListAdapter : ListAdapter<PaymentUi, PaymentUiHolder>(DiffUtilCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentUiHolder {
        val viewBinding = ListItemPaymentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PaymentUiHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: PaymentUiHolder, position: Int) {
        val paymentUi = getItem(position)
        holder.bind(paymentUi)
    }
}

private class DiffUtilCallBack : DiffUtil.ItemCallback<PaymentUi>() {
    override fun areItemsTheSame(oldItem: PaymentUi, newItem: PaymentUi): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: PaymentUi, newItem: PaymentUi): Boolean {
        return oldItem == newItem
    }

}