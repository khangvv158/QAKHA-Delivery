package com.sun.qakhadelivery.screens.partner.tabs.product

class ProductPartnerPresenter : ProductPartnerContract.Presenter {

    private var view: ProductPartnerContract.View? = null

    override fun onStart() = Unit

    override fun onStop() = Unit

    override fun setView(view: ProductPartnerContract.View?) {
        this.view = view
    }
}
