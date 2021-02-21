package com.ssho.aeontest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ssho.aeontest.di.AppModule.isUserLoggedIn
import com.ssho.aeontest.di.AppModule.navigator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigator.activity = this

        val isFragmentContainerEmpty = savedInstanceState == null

        if (isFragmentContainerEmpty) {
            if (isUserLoggedIn())
                navigator.successfulLogin()
            else
                navigator.login()
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        navigator.activity = null
    }
}