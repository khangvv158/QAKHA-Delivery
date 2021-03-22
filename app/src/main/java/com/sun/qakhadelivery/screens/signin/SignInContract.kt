package com.sun.qakhadelivery.screens.signin

import com.sun.qakhadelivery.utils.BasePresenter

interface SignInContract {

    interface View {

        fun onSignInSuccess()
        fun onSignInFailure(message: String)
        fun onError(message: String)
    }

    interface Presenter : BasePresenter<View> {
        fun signIn(email: String, password: String)
    }
}
