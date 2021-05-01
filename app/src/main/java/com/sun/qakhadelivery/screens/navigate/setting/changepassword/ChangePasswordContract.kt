package com.sun.qakhadelivery.screens.navigate.setting.changepassword

import com.sun.qakhadelivery.data.source.remote.schema.request.ChangePasswordRequest
import com.sun.qakhadelivery.utils.BasePresenter

interface ChangePasswordContract {

    interface View {

        fun onChangePasswordSuccess()
        fun onChangePasswordSuccessFailure(message: String)
        fun onError(message: String)
    }

    interface Presenter : BasePresenter<View> {

        fun changePassword(changePasswordRequest: ChangePasswordRequest)
    }
}
