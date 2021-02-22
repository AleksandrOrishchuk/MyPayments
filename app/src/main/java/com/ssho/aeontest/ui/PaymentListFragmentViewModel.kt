package com.ssho.aeontest.ui

import android.util.Log
import androidx.lifecycle.*
import com.ssho.aeontest.Navigator
import com.ssho.aeontest.domain.usecase.GetUserPaymentsUseCase
import com.ssho.aeontest.domain.usecase.UnauthorizeUserUseCase
import com.ssho.aeontest.ui.model.PaymentUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "PaymentListViewModel"

class PaymentListFragmentViewModel(
    private val paymentUiMapper: PaymentUiMapper,
    private val getUserPayments: GetUserPaymentsUseCase,
    override val unauthorizeUser: UnauthorizeUserUseCase,
    override val navigator: Navigator,
) : SuccessfulAuthViewModel() {
    val viewState: LiveData<PaymentsViewState> get() = _viewState
    private val _viewState = MutableLiveData<PaymentsViewState>()

    init {
        onLoadPayments()
    }

    fun onLoadPayments() {
        viewModelScope.launch {
            postLoadingViewState()
            withContext(Dispatchers.IO) {
                runCatching {
                    val paymentList = getUserPayments()
                    val paymentUiList = paymentList.map(paymentUiMapper)
                    postResultViewState(paymentUiList)
                }.onFailure { error ->
                    postNetworkErrorViewState()
                    Log.e(TAG, error.message, error)
                }
            }
        }
    }

    private fun postResultViewState(paymentUiList: List<PaymentUi>) {
        _viewState.postValue(
            PaymentsViewState.Result(
                paymentUiList
            )
        )
    }

    private fun postLoadingViewState() {
        _viewState.postValue(PaymentsViewState.Loading)
    }

    private fun postNetworkErrorViewState() {
        _viewState.postValue(PaymentsViewState.NetworkError)
    }
}

sealed class PaymentsViewState {
    data class Result(val paymentUiList: List<PaymentUi>) : PaymentsViewState()
    object Loading : PaymentsViewState()
    object NetworkError : PaymentsViewState()
}

@Suppress("UNCHECKED_CAST")
class PaymentListFragmentViewModelFactory(
    private val paymentUiMapper: PaymentUiMapper,
    private val getUserPaymentsUseCase: GetUserPaymentsUseCase,
    private val unauthorizeUser: UnauthorizeUserUseCase,
    private val navigator: Navigator
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PaymentListFragmentViewModel(
            paymentUiMapper,
            getUserPaymentsUseCase,
            unauthorizeUser,
            navigator
        ) as T
    }
}