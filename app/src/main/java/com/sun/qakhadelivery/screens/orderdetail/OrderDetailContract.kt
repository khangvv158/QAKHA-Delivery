package com.sun.qakhadelivery.screens.orderdetail

import com.sun.qakhadelivery.data.source.remote.schema.response.OrderDetailsResponse
import com.sun.qakhadelivery.utils.BasePresenter

interface OrderDetailContract {

    interface View {

        fun onSuccessOrderDetails(orderDetailsResponse: OrderDetailsResponse)

        fun onErrorOrderDetails(exception: String)
    }

    interface Presenter : BasePresenter<View> {

        fun getOrderDetail(orderId: Int)
    }
}
