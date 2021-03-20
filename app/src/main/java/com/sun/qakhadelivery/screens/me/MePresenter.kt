package com.sun.qakhadelivery.screens.me

class MePresenter : MeContract.Presenter {

    private var view: MeContract.View? = null

    override fun onStart() = Unit

    override fun onStop() = Unit

    override fun setView(view: MeContract.View?) {
        this.view = view
    }
}
