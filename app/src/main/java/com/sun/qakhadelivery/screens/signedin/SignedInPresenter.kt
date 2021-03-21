package com.sun.qakhadelivery.screens.signedin

class SignedInPresenter : SignedInContract.Presenter {

    private var view: SignedInContract.View? = null

    override fun onStart() = Unit

    override fun onStop() = Unit

    override fun setView(view: SignedInContract.View?) {
        this.view = view
    }
}
