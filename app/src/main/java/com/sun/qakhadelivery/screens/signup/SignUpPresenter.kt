package com.sun.qakhadelivery.screens.signup

class SignUpPresenter : SignUpContract.Presenter {

    private var view: SignUpContract.View? = null

    override fun onStart() = Unit

    override fun onStop() = Unit

    override fun setView(view: SignUpContract.View?) {
        this.view = view
    }
}
