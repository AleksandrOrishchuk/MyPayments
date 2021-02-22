package com.ssho.aeontest.ui

import androidx.lifecycle.ViewModelProvider

class PaymentListFragment : SuccessfulAuthFragment() {
    companion object {
        fun newInstance(): PaymentListFragment = PaymentListFragment()
    }

    override val viewModel: SuccessfulAuthViewModel by lazy {
        ViewModelProvider(this).get(PaymentListFragmentViewModel::class.java)
    }
}