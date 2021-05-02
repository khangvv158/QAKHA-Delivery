package com.sun.qakhadelivery.extensions

import android.os.SystemClock
import android.view.View

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.isVisible(): Boolean {
    return visibility == View.VISIBLE
}

fun View.enable() {
    isEnabled = true
}

fun View.disable() {
    isEnabled = false
}

fun View.setOnSafeClickListener(interval: Int = 1000, func: (View) -> Unit) {
    var timeLeft = 0L
    setOnClickListener {
        if (SystemClock.elapsedRealtime() - timeLeft < interval) {
            return@setOnClickListener
        }
        timeLeft = SystemClock.elapsedRealtime()
        func(it)
    }
}

fun View.setOnSafeClickListener(interval: Int = 1000, listener: View.OnClickListener) {
    var timeLeft = 0L
    setOnClickListener {
        if (SystemClock.elapsedRealtime() - timeLeft < interval) {
            return@setOnClickListener
        }
        timeLeft = SystemClock.elapsedRealtime()
        listener.onClick(it)
    }
}
