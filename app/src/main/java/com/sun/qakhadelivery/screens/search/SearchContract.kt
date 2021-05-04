package com.sun.qakhadelivery.screens.search

import com.sun.qakhadelivery.data.model.Partner
import com.sun.qakhadelivery.utils.BasePresenter

interface SearchContract {

    interface View {

        fun getPartnersSuccess(partners: MutableList<Partner>)
        fun onError(message: String)
    }

    interface Presenter : BasePresenter<View> {

        fun getPartners()
    }
}
