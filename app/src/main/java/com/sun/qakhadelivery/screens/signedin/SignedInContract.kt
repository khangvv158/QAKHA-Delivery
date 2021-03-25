package com.sun.qakhadelivery.screens.signedin

import com.sun.qakhadelivery.data.model.User
import com.sun.qakhadelivery.utils.BasePresenter

interface SignedInContract {

    interface View {
        fun onGetUserSuccess(user: User)
        fun onGetUserFailure()
        fun onSignOutSuccess()
        fun onError(message: String)
    }

    interface Presenter : BasePresenter<View> {
        fun getUser()
        fun signOut()
    }
}
