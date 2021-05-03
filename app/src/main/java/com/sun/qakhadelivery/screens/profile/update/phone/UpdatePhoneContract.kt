package com.sun.qakhadelivery.screens.profile.update.phone

import com.sun.qakhadelivery.data.model.User
import com.sun.qakhadelivery.data.source.remote.schema.request.UpdatePhone
import com.sun.qakhadelivery.utils.BasePresenter

interface UpdatePhoneContract {

    interface Presenter : BasePresenter<View> {

        fun updatePhone(updatePhone: UpdatePhone)
    }

    interface View {

        fun onSuccessPhone(user: User)

        fun onErrorPhone(exception: String)
    }
}
