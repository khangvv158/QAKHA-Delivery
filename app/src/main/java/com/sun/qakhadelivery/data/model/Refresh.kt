package com.sun.qakhadelivery.data.model

data class Refresh(
    val from: Class<*>,
    val to: Class<*>
) {

    fun message(to: Class<*>, result: (Refresh) -> Unit) {
        if (this.to.simpleName == to.simpleName) {
            result(this)
        }
    }

    fun message(result: (Refresh) -> Unit) {
        result(this)
    }
}
