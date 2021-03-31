package com.sun.qakhadelivery.screens.forgotpassword

import com.sun.qakhadelivery.utils.BasePresenter

interface ForgotPasswordContract {

    interface View {
        fun onForgotPasswordSuccess(email: String)
        fun onForgotPasswordFailure()
        fun onError(message: String)
    }

    interface Presenter : BasePresenter<View> {
        fun forgotPassword(email: String)
    }
}
