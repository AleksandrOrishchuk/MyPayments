package com.ssho.aeontest.ui.payments_ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssho.aeontest.databinding.FragmentPaymentListBinding
import com.ssho.aeontest.di.AppModule.providePaymentListViewModelFactory
import com.ssho.aeontest.ui.SuccessfulAuthFragment

class PaymentListFragment : SuccessfulAuthFragment() {
    companion object {
        fun newInstance(): PaymentListFragment = PaymentListFragment()
    }

    private lateinit var fragmentBinding: FragmentPaymentListBinding
    override val viewModel: PaymentListFragmentViewModel by lazy {
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

        fragmentBinding.paymentsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = PaymentUiListAdapter()
        }

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentBinding.retryButton.setOnClickListener {
            viewModel.onLoadPayments()
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.viewState.observe(viewLifecycleOwner) {
            applyViewState(it)
        }

    }

    private fun applyViewState(viewState: PaymentsViewState?) {
        fragmentBinding.apply {

            loadingProgressBar.isVisible = viewState is PaymentsViewState.Loading
            errorContainer.isVisible = viewState is PaymentsViewState.NetworkError
            paymentsRecyclerView.isVisible = viewState is PaymentsViewState.Result

            if (viewState is PaymentsViewState.Result) {
                (paymentsRecyclerView.adapter as PaymentUiListAdapter)
                    .submitList(
                        viewState.paymentUiList
                    )
            }
        }
    }

}