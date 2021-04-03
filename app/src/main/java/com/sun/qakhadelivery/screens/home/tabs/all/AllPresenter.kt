package com.sun.qakhadelivery.screens.home.tabs.all

class AllPresenter : AllContract.Presenter {

    private var view: AllContract.View? = null

    override fun onStart() = Unit

    override fun onStop() = Unit

    override fun setView(view: AllContract.View?) {
        this.view = view
    }
}
