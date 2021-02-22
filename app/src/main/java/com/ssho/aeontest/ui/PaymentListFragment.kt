package com.ssho.aeontest.ui

import androidx.lifecycle.ViewModelProvider
import com.ssho.aeontest.di.AppModule.providePaymentListViewModelFactory

class PaymentListFragment : SuccessfulAuthFragment() {
    companion object {
        fun newInstance(): PaymentListFragment = PaymentListFragment()
    }

    override val viewModel: SuccessfulAuthViewModel by lazy {
        ViewModelProvider(this, providePaymentListViewModelFactory()).get(
            PaymentListFragmentViewModel::class.java
        )
    }
}