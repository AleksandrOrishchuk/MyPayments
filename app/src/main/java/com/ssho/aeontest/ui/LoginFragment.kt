package com.ssho.aeontest.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ssho.aeontest.R
import com.ssho.aeontest.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var loginBinding: FragmentLoginBinding
    private val loginViewModel: ViewModel by lazy {
        ViewModelProvider(this).get(LoginFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        loginBinding = FragmentLoginBinding.inflate(inflater, container, false)

        return loginBinding.root
    }
}