package com.sun.qakhadelivery.screens.home

import com.sun.qakhadelivery.data.model.TypePartner
import com.sun.qakhadelivery.utils.BasePresenter

interface HomeContract {

    interface View {

        fun onGetTypesSuccess(types: MutableList<TypePartner>)

        fun onError(message: String)
    }

    interface Presenter : BasePresenter<View> {

        fun getTypes()
    }
}
