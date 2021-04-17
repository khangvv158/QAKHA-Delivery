package com.sun.qakhadelivery.screens.partner.tabs.review

import com.sun.qakhadelivery.data.model.Feedback
import com.sun.qakhadelivery.utils.BasePresenter

interface ReviewContract {

    interface View {

        fun onGetFeedbackSuccess(feedback: Feedback)
        fun onError(message: String)
    }

    interface Presenter : BasePresenter<View> {

        fun getFeedback(idPartner: Int)
    }
}
