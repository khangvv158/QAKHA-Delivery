package com.sun.qakhadelivery.screens.plash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.screens.main.MainActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler(mainLooper).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
            finish()
        }, SPLASH_DISPLAY_TIME)
    }

    companion object {
        private const val SPLASH_DISPLAY_TIME = 1000L
    }
}
