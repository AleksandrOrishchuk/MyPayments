package com.ssho.aeontest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ssho.aeontest.di.AppModule.getCurrentUserUseCase
import com.ssho.aeontest.di.AppModule.navigator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigator.activity = this

        val isFragmentContainerEmpty = savedInstanceState == null
        val isUserLoggedIn = getCurrentUserUseCase() != null

        if (isFragmentContainerEmpty) {
            if (isUserLoggedIn)
                navigator.userPayments()
            else
                navigator.login()
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        navigator.activity = null
    }
}