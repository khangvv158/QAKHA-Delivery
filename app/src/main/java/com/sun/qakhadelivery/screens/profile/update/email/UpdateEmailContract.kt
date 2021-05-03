package com.sun.qakhadelivery.screens.profile.update.email

import com.sun.qakhadelivery.data.model.User
import com.sun.qakhadelivery.data.source.remote.schema.request.UpdateEmail
import com.sun.qakhadelivery.data.source.remote.schema.response.MessageResponse
import com.sun.qakhadelivery.data.source.remote.schema.response.VerifyEmail
import com.sun.qakhadelivery.utils.BasePresenter

interface UpdateEmailContract {

    interface Presenter : BasePresenter<View> {

        fun sendCodeEmail(updateEmail: UpdateEmail)

        fun verifyEmail(verifyEmail: VerifyEmail)
    }

    interface View {

        fun onSuccessSendCodeEmail(user: User)

        fun onErrorSendCodeEmail(exception: String)

        fun onSuccessVerifyEmail(messageResponse: MessageResponse)

        fun onErrorVerifyEmail(exception: String)
    }
}
