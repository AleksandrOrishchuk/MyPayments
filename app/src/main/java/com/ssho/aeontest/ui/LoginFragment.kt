package com.ssho.aeontest.ui

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ssho.aeontest.databinding.FragmentLoginBinding
import com.ssho.aeontest.di.AppModule.provideLoginViewModelFactory

class LoginFragment : Fragment() {
    companion object {
        fun newInstance(): LoginFragment {
            return LoginFragment()
        }
    }

    private lateinit var loginBinding: FragmentLoginBinding
    private val loginViewModel: LoginFragmentViewModel by lazy {
        ViewModelProvider(
            this,
            provideLoginViewModelFactory()
        ).get(LoginFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loginBinding = FragmentLoginBinding.inflate(inflater, container, false)

        return loginBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginBinding.apply {

            loginButton.setOnClickListener {
                loginViewModel.login()
            }

            rememberMeCheckbox.setOnCheckedChangeListener { _, isChecked ->
                loginViewModel.onRememberMeChecked(isChecked)
            }

            with(loginViewModel.loginUi) {
                loginEditText.setText(login)
                passwordEditText.setText(password)
                rememberMeCheckbox.isChecked = isRememberMeChecked
            }

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        loginViewModel.loginViewState.observe(viewLifecycleOwner) {
            applyViewState(it)
        }
    }

    override fun onStart() {
        super.onStart()
        loginBinding.apply {

            loginEditText.doAfterTextChanged { text ->
                text?.also {
                    loginViewModel.onLoginFieldUpdated(it.toString())
                }
            }

            passwordEditText.doAfterTextChanged { text->
                text?.also {
                    loginViewModel.onPasswordFieldUpdated(it.toString())
                }
            }
        }
    }

    private fun applyViewState(viewState: LoginViewState?) {
        loginBinding.apply {

            networkErrorTextView.isVisible = viewState is LoginViewState.NetworkError
            failedLoginTextView.isVisible = viewState is LoginViewState.FailedLogging
            loadingProgressBar.isVisible = viewState is LoginViewState.Loading

//            when (viewState) {
//                null -> Unit
//                LoginViewState.Loading -> Unit
//                else -> with(viewState as LoginViewState.RegularLogging) {
//                    loginEditText.setText(loginUi.login)
//                    passwordEditText.setText(loginUi.password)
//                    rememberMeCheckbox.isChecked = loginUi.isRememberMeChecked
//                }
//            }
        }
    }
}