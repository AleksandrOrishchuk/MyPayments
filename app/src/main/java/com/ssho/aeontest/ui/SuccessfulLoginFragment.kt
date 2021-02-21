package com.ssho.aeontest.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.ssho.aeontest.R
import com.ssho.aeontest.databinding.FragmentSuccessfulLoginBinding

class SuccessfulLoginFragment : Fragment() {
    companion object {
        fun newInstance(): SuccessfulLoginFragment {
            return SuccessfulLoginFragment()
        }
    }

    private lateinit var viewBinding: FragmentSuccessfulLoginBinding

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}