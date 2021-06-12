package com.sun.qakhadelivery.screens.signup.activated

import com.sun.qakhadelivery.data.source.remote.schema.request.EmailRequest
import com.sun.qakhadelivery.utils.BasePresenter

interface ActivatedContract {

    interface View {

        fun onActivateAccountSuccess()
        fun onActivateAccountFailure()
        fun onResendCodeActivateSuccess()
        fun onResendCodeActivateFailure()
        fun onError(message: String)
    }

    interface Presenter : BasePresenter<View> {

        fun activateAccount(codeActivate: String)
        fun resendCodeActivate(emailRequest: EmailRequest)
    }
}
