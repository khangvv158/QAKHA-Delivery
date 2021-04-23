package com.sun.qakhadelivery.screens.order.tabs.history

import com.sun.qakhadelivery.data.source.remote.schema.response.HistoryResponse
import com.sun.qakhadelivery.utils.BasePresenter

interface HistoryContract {

    interface View {

        fun onSuccessGetHistory(history: MutableList<HistoryResponse>)

        fun onErrorGetHistory(exception: String)
    }

    interface Presenter : BasePresenter<View> {

        fun getHistory()
    }
}
