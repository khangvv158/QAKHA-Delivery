package com.sun.qakhadelivery.screens.partner

class PartnerPresenter : PartnerContract.Presenter {

    private var view: PartnerContract.View? = null

    override fun onStart() = Unit

    override fun onStop() = Unit

    override fun setView(view: PartnerContract.View?) {
        this.view = view
    }
}
