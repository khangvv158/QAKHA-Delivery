package com.sun.qakhadelivery.screens.signup

import com.sun.qakhadelivery.utils.BasePresenter

interface SignUpContract {

    interface View {

        fun onSignUpSuccess()
        fun onSignUpFailure(message: String)
        fun onCheckEmailIsExistSuccess()
        fun onCheckEmailIsExistFailure()
        fun onCheckPhoneNumberIsExistSuccess()
        fun onCheckPhoneNumberIsExistFailure()
        fun onError(message: String)
    }

    interface Presenter : BasePresenter<View> {

        fun signUp(email: String,
                   password: String,
                   passwordConfirmation: String,
                   phoneNumber: String,
                   name: String
        )
        fun checkEmailIsExist(email: String)
        fun checkPhoneNumberIsExist(phoneNumber: String)
    }
}
