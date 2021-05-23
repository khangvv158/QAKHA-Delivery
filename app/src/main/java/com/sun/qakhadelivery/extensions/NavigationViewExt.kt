package com.sun.qakhadelivery.extensions

import android.os.SystemClock
import com.google.android.material.navigation.NavigationView

fun NavigationView.setOnSafeNavigationItemSelectedListener(
    interval: Int = 2000,
    listener: NavigationView.OnNavigationItemSelectedListener
) {
    var timeLeft = 0L
    setNavigationItemSelectedListener {
        if (SystemClock.elapsedRealtime() - timeLeft < interval) {
            return@setNavigationItemSelectedListener false
        }
        timeLeft = SystemClock.elapsedRealtime()
        listener.onNavigationItemSelected(it)
        true
    }
}
