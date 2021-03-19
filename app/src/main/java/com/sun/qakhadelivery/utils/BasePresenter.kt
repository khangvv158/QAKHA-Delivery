package com.sun.qakhadelivery.utils

interface BasePresenter<T> {

    fun onStart()
    fun onStop()
    fun setView(view: T?)
}
