package com.sun.qakhadelivery.screens.shippingdetail

class ShippingDetailPresenter : ShippingDetailContract.Presenter {

    private var view: ShippingDetailContract.View? = null

    override fun onStart() {
    }

    override fun onStop() {
    }

    override fun setView(view: ShippingDetailContract.View?) {
        this.view = view
    }
}
