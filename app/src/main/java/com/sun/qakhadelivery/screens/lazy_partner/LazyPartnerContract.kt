package com.sun.qakhadelivery.screens.lazy_partner

import com.sun.qakhadelivery.data.source.remote.schema.response.PartnerResponse
import com.sun.qakhadelivery.utils.BasePresenter

interface LazyPartnerContract {

    interface View {

        fun onErrorGetPartnerById(exception: String)

        fun onSuccessGetPartnerById(partnerResponse: PartnerResponse)
    }

    interface Presenter : BasePresenter<View> {

        fun getPartnerById(id: Int)
    }
}
