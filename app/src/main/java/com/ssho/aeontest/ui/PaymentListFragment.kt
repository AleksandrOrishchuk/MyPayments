package com.ssho.aeontest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ssho.aeontest.databinding.FragmentPaymentListBinding
import com.ssho.aeontest.di.AppModule.providePaymentListViewModelFactory

class PaymentListFragment : SuccessfulAuthFragment() {
    companion object {
        fun newInstance(): PaymentListFragment = PaymentListFragment()
    }

    private lateinit var fragmentBinding: FragmentPaymentListBinding
    override val viewModel: SuccessfulAuthViewModel by lazy {
        ViewModelProvider(this, providePaymentListViewModelFactory()).get(
            PaymentListFragmentViewModel::class.java
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        fragmentBinding = FragmentPaymentListBinding.inflate(inflater, container, false)

        return fragmentBinding.root
    }


}