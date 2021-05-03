package com.sun.qakhadelivery.screens.profile.update.name

import com.sun.qakhadelivery.data.model.User
import com.sun.qakhadelivery.data.source.remote.schema.request.UpdateUsername
import com.sun.qakhadelivery.utils.BasePresenter

interface UpdateNameContract {

    interface Presenter : BasePresenter<View> {

        fun updateUsername(updateUsername: UpdateUsername)
    }

    interface View {

        fun onSuccessUsername(user: User)

        fun onErrorUsername(exception: String)
    }
}
