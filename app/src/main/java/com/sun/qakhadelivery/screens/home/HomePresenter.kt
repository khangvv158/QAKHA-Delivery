package com.sun.qakhadelivery.screens.home

class HomePresenter : HomeContract.Presenter {

    private var view: HomeContract.View? = null

    override fun onStart() = Unit

    override fun onStop() = Unit

    override fun setView(view: HomeContract.View?) {
        this.view = view
    }
}
