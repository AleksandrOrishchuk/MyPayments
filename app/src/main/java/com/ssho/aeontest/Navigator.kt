package com.ssho.aeontest

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ssho.aeontest.ui.auth_ui.AuthFragment
import com.ssho.aeontest.ui.payments_ui.PaymentListFragment

class Navigator {
    var activity: AppCompatActivity? = null

    private fun replaceFragment(fragment: Fragment) {
        activity?.supportFragmentManager?.also {
            it.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }
    }

    fun login() {
        replaceFragment(AuthFragment.newInstance())
    }

    fun userPayments() {
        replaceFragment(PaymentListFragment.newInstance())
    }
}