package com.sun.qakhadelivery.screens.home.tabs.nearby

import com.sun.qakhadelivery.data.model.Partner
import com.sun.qakhadelivery.data.source.remote.schema.request.LocationRequest
import com.sun.qakhadelivery.data.source.remote.schema.response.PartnerResponse
import com.sun.qakhadelivery.utils.BasePresenter

interface NearbyContract {

    interface View {

        fun getSuggestPartnerNearbySuccess(partners: MutableList<Partner>)

        fun onError(message: String)
    }

    interface Presenter : BasePresenter<View> {

        fun getSuggestPartnerNearby(locationRequest: LocationRequest)
    }
}
