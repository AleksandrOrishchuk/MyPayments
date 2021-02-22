package com.ssho.aeontest.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.ssho.aeontest.R

abstract class SuccessfulAuthFragment : Fragment() {

    abstract val viewModel: SuccessfulAuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        return super.onCreateView(inflater, container, savedInstanceState)
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