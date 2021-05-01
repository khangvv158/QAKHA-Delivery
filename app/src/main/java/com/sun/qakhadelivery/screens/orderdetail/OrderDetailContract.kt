package com.sun.qakhadelivery.screens.orderdetail

import com.sun.qakhadelivery.data.source.remote.schema.response.OrderDetailsResponse
import com.sun.qakhadelivery.data.source.remote.schema.response.RateDriver
import com.sun.qakhadelivery.utils.BasePresenter

interface OrderDetailContract {

    interface View {

        fun onSuccessOrderDetails(orderDetailsResponse: OrderDetailsResponse)

        fun onSuccessCheckDriverFeedback(rateDriver: RateDriver)

        fun onErrorOrderDetails(exception: String)

        fun onErrorCheckDriverFeedback(exception: String)
    }

    interface Presenter : BasePresenter<View> {

        fun getOrderDetail(orderId: Int)

        fun checkDriverFeedback(orderId: Int)
    }
}
