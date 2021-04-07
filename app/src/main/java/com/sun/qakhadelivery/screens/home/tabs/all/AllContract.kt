package com.sun.qakhadelivery.screens.home.tabs.all

import com.sun.qakhadelivery.data.model.Partner
import com.sun.qakhadelivery.utils.BasePresenter

interface AllContract {

    interface Presenter : BasePresenter<View> {

        fun getPartners()
    }

    interface View {

        fun onSuccessGetPartners(partners: MutableList<Partner>)

        fun onErrorGetPartners(exception: String)
    }
}
