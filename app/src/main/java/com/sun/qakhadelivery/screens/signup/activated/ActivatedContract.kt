package com.sun.qakhadelivery.screens.signup.activated

import com.sun.qakhadelivery.utils.BasePresenter

interface ActivatedContract {

    interface View {

        fun onActivateAccountSuccess()
        fun onActivateAccountFailure()
    }

    interface Presenter : BasePresenter<View> {

        fun activateAccount(codeActivate: String)
    }
}
