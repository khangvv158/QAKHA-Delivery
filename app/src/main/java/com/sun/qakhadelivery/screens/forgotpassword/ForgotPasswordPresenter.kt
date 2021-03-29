package com.sun.qakhadelivery.screens.forgotpassword

class ForgotPasswordPresenter : ForgotPasswordContract.Presenter {

    private var view: ForgotPasswordContract.View? = null

    override fun onStart() = Unit

    override fun onStop() = Unit

    override fun setView(view: ForgotPasswordContract.View?) {
        this.view = view
    }
}
