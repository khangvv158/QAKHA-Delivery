package com.sun.qakhadelivery.data.model

import android.os.Bundle

data class Refresh(
    val from: Class<*>,
    val to: Class<*>,
    var data: Bundle? = null
) {

    fun message(to: Class<*>, result: (Refresh) -> Unit) {
        if (this.to.simpleName == to.simpleName) {
            result(this)
        }
    }

    fun message(from: Class<*>, to: Class<*>, result: (Refresh) -> Unit) {
        if (this.to.simpleName == to.simpleName && this.from.simpleName == from.simpleName) {
            result(this)
        }
    }

    fun message(result: (Refresh) -> Unit) {
        result(this)
    }
}
