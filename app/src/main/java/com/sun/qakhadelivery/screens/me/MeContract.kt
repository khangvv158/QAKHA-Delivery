package com.sun.qakhadelivery.screens.me

import com.sun.qakhadelivery.utils.BasePresenter

interface MeContract {

    interface View {
        fun onCheckSignedInSuccess()
        fun onCheckSignedInFailure()
    }

    interface Presenter : BasePresenter<View> {
        fun checkSignedIn()
    }
}
