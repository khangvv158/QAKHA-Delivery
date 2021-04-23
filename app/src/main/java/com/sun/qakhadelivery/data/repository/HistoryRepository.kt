package com.sun.qakhadelivery.data.repository

import com.sun.qakhadelivery.data.source.remote.HistoryAPI
import com.sun.qakhadelivery.data.source.remote.RetrofitClient
import com.sun.qakhadelivery.data.source.remote.schema.response.HistoryResponse
import com.sun.qakhadelivery.data.source.remote.schema.response.OrderDetailsResponse
import io.reactivex.rxjava3.core.Observable

interface HistoryRepository {

    fun getHistory(token: String): Observable<MutableList<HistoryResponse>>

    fun getOrderDetails(orderId: Int, token: String):  Observable<OrderDetailsResponse>
}

class HistoryRepositoryImpl : HistoryRepository {

    private val client = RetrofitClient.getInstance().create(HistoryAPI::class.java)

    override fun getHistory(token: String): Observable<MutableList<HistoryResponse>> {
        return client.getHistory(token)
    }

    override fun getOrderDetails(orderId: Int, token: String): Observable<OrderDetailsResponse> {
        return client.getOrderDetails(orderId, token)
    }

    companion object {

        private var instance: HistoryRepositoryImpl? = null

        fun getInstance(): HistoryRepositoryImpl =
            instance ?: synchronized(this) {
                instance ?: HistoryRepositoryImpl().also {
                    instance = it
                }
            }
    }
}
