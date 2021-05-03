package com.sun.qakhadelivery.screens.profile

import com.sun.qakhadelivery.data.model.User
import com.sun.qakhadelivery.data.source.remote.schema.request.UpdateImage
import com.sun.qakhadelivery.utils.BasePresenter

interface ProfileContract {

    interface Presenter : BasePresenter<View> {

        fun getUser()

        fun uploadImage(file: String)

        fun updateImage(updateImage: UpdateImage)
    }

    interface View {

        fun onSuccessUser(user: User)

        fun onSuccessUploadImage(url: String)

        fun onSuccessUpdateImage(user: User)

        fun onErrorUser(exception: String)

        fun onErrorUploadImage(exception: String)

        fun onErrorUpdateImage(exception: String)
    }
}
