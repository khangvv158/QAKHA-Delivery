package com.sun.qakhadelivery.screens.order.tabs.shipping

import com.sun.qakhadelivery.data.source.remote.schema.response.HistoryResponse
import com.sun.qakhadelivery.data.source.remote.schema.response.OrderResponse
import com.sun.qakhadelivery.utils.BasePresenter

interface ShippingContract {

    interface View {

        fun onSuccessGetShipping(shipping: MutableList<HistoryResponse>)

        fun onErrorGetShipping(exception: String)

        fun onSuccessTrackingOrder(orderResponse: OrderResponse)

        fun onErrorTrackingOrder(exception: String)
    }

    interface Presenter : BasePresenter<View> {

        fun getShipping()

        fun trackingOrder(orderId: Int)
    }
}
