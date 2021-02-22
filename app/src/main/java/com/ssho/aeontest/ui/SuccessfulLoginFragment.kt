package com.ssho.aeontest.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ssho.aeontest.R
import com.ssho.aeontest.databinding.FragmentSuccessfulLoginBinding
import com.ssho.aeontest.di.AppModule.provideSuccessfulAuthViewModelFactory

class SuccessfulLoginFragment : Fragment() {
    companion object {
        fun newInstance(): SuccessfulLoginFragment {
            return SuccessfulLoginFragment()
        }
    }

    private lateinit var viewBinding: FragmentSuccessfulLoginBinding
    private val viewModel: SuccessfulLoginViewModel by lazy {
        ViewModelProvider(this, provideSuccessfulAuthViewModelFactory()).get(
            SuccessfulLoginViewModel::class.java
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        viewBinding = FragmentSuccessfulLoginBinding.inflate(inflater, container, false)

        return viewBinding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_successful_login, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_logout -> viewModel.logout()
            else -> super.onOptionsItemSelected(item)
        }
    }

}