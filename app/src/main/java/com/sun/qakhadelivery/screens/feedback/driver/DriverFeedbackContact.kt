package com.sun.qakhadelivery.screens.feedback.driver

import com.sun.qakhadelivery.data.source.remote.schema.request.RateDriverRequest
import com.sun.qakhadelivery.data.source.remote.schema.response.RateDriverResponse
import com.sun.qakhadelivery.utils.BasePresenter

interface DriverFeedbackContact {

    interface View {

        fun onSuccessFeedback(rateDriverResponse: RateDriverResponse)

        fun onErrorFeedback(exception: String)
    }

    interface Presenter : BasePresenter<View> {

        fun feedbackDriver(rateDriverRequest: RateDriverRequest)
    }
}
