package com.sun.qakhadelivery.screens.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.widget.Toast
import com.sun.qakhadelivery.R

class MainActivity : AppCompatActivity() {

    private var doubleBackPressed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setFlags()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else if (!doubleBackPressed) {
            this.doubleBackPressed = true
            Toast.makeText(
                    this,
                    getString(R.string.back_again_to_exit),
                    Toast.LENGTH_SHORT
            ).show()
            Handler().postDelayed(Runnable { doubleBackPressed = false }, 2000)
            return
        } else {
            super.onBackPressed()
            return
        }
    }

    private fun setFlags() {
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        supportActionBar?.hide()
    }
}
