package com.sun.qakhadelivery.screens.home.tabs.bestrated

import com.sun.qakhadelivery.data.model.Partner
import com.sun.qakhadelivery.data.source.remote.schema.response.PartnerResponse
import com.sun.qakhadelivery.utils.BasePresenter

interface BestRatedContract {

    interface View {

        fun getSuggestPartnerBestRatedSuccess(partners: MutableList<Partner>)
        fun getPartnerByIdSuccess(partnerResponse: PartnerResponse)
        fun onError(message: String)
    }

    interface Presenter : BasePresenter<View> {

        fun getSuggestPartnerBestRated()
        fun getPartnerById(idPartner: Int)
    }
}
