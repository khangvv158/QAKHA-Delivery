package com.sun.qakhadelivery.screens.lazy_order_details

import com.sun.qakhadelivery.data.model.OrderDetailMerge
import com.sun.qakhadelivery.data.source.remote.schema.response.HistoryResponse
import com.sun.qakhadelivery.utils.BasePresenter

interface LazyOrderDetailsContract {

    interface View {

        fun onSuccessOrderDetails(orderDetailMerge: OrderDetailMerge)

        fun onErrorOrderDetails(exception: String)
    }

    interface Presenter : BasePresenter<View> {

        fun getOrderDetail(orderId: Int, historyResponse: HistoryResponse)
    }
}
