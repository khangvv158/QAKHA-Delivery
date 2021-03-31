package com.sun.qakhadelivery.screens.forgotpassword.resetpassword

import com.sun.qakhadelivery.utils.BasePresenter

interface ResetPasswordContract {

    interface View {
        fun onResetPasswordSuccess()
        fun onResetPasswordFailure()
        fun onGeneratingCodeSuccess()
        fun onGeneratingCodeFailure()
    }

    interface Presenter : BasePresenter<View> {
        fun resetPassword(newPassword: String, verificationCode: String)
        fun generatingCode(email: String)
    }
}
