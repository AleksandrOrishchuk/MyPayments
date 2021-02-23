package com.ssho.aeontest.ui.auth_ui

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ssho.aeontest.databinding.FragmentAuthBinding
import com.ssho.aeontest.di.AppModule.provideAuthViewModelFactory

class AuthFragment : Fragment() {
    companion object {
        fun newInstance(): AuthFragment {
            return AuthFragment()
        }
    }

    private lateinit var authFragmentBinding: FragmentAuthBinding
    private val authViewModel: AuthFragmentViewModel by lazy {
        ViewModelProvider(
            this,
            provideAuthViewModelFactory()
        ).get(AuthFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        authFragmentBinding = FragmentAuthBinding.inflate(inflater, container, false)

        return authFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authFragmentBinding.apply {

            loginButton.setOnClickListener {
                authViewModel.login()
            }

            rememberMeCheckbox.setOnCheckedChangeListener { _, isChecked ->
                authViewModel.onRememberMeChecked(isChecked)
            }

            with(authViewModel.authUiData) {
                loginEditText.setText(login)
                passwordEditText.setText(password)
                rememberMeCheckbox.isChecked = isRememberMeChecked
                rememberMeCheckbox.jumpDrawablesToCurrentState()
            }

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        authViewModel.authorizationViewState.observe(viewLifecycleOwner) {
            applyViewState(it)
        }
    }

    override fun onStart() {
        super.onStart()
        authFragmentBinding.apply {

            loginEditText.doAfterTextChanged { text ->
                text?.also {
                    authViewModel.onLoginFieldUpdated(it.toString())
                }
            }

            passwordEditText.doAfterTextChanged { text->
                text?.also {
                    authViewModel.onPasswordFieldUpdated(it.toString())
                }
            }
        }
    }

    private fun applyViewState(viewState: AuthorizationViewState?) {
        authFragmentBinding.apply {

            networkErrorTextView.isVisible = viewState is AuthorizationViewState.NetworkError
            failedLoginTextView.isVisible = viewState is AuthorizationViewState.FailedLogging
            loadingProgressBar.isVisible = viewState is AuthorizationViewState.Loading

        }
    }
}