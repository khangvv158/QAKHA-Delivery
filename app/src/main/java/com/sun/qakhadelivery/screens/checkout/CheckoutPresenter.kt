package com.sun.qakhadelivery.screens.checkout

class CheckoutPresenter : CheckoutContract.Presenter {

    private var view: CheckoutContract.View? = null

    override fun onStart() = Unit

    override fun onStop() = Unit

    override fun setView(view: CheckoutContract.View?) {
        this.view = view
    }
}
