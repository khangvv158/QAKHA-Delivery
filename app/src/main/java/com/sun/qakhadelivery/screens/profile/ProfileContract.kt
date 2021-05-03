package com.sun.qakhadelivery.screens.profile

import com.sun.qakhadelivery.data.model.User
import com.sun.qakhadelivery.utils.BasePresenter

interface ProfileContract {

    interface Presenter : BasePresenter<View> {

        fun getUser()
    }

    interface View {

        fun onSuccessUser(user: User)

        fun onErrorUser(exception: String)
    }
}
