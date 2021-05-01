package com.sun.qakhadelivery.screens.feedback.partner

import com.sun.qakhadelivery.data.source.remote.schema.request.RatePartnerRequest
import com.sun.qakhadelivery.data.source.remote.schema.response.RatePartnerResponse
import com.sun.qakhadelivery.utils.BasePresenter

interface PartnerFeedbackContact {

    interface View {

        fun onSuccessFeedbackPartner(ratePartnerResponse: RatePartnerResponse)

        fun onErrorFeedbackPartner(exception: String)
    }

    interface Presenter : BasePresenter<View> {

        fun feedbackPartner(ratePartnerRequest: RatePartnerRequest)
    }
}
