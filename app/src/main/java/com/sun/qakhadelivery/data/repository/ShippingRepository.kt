package com.sun.qakhadelivery.data.repository

import com.sun.qakhadelivery.data.source.remote.RetrofitClient
import com.sun.qakhadelivery.data.source.remote.ShippingAPI
import com.sun.qakhadelivery.data.source.remote.schema.response.HistoryResponse
import com.sun.qakhadelivery.data.source.remote.schema.response.OrderResponse
import io.reactivex.rxjava3.core.Observable

interface ShippingRepository {

    fun getShipping(token: String): Observable<MutableList<HistoryResponse>>

    fun trackingOrder(orderId: Int, token: String): Observable<OrderResponse>
}

class ShippingRepositoryImpl : ShippingRepository {

    private val client = RetrofitClient.getInstance().create(ShippingAPI::class.java)

    override fun getShipping(token: String): Observable<MutableList<HistoryResponse>> {
        return client.getShipping(token)
    }

    override fun trackingOrder(orderId: Int, token: String): Observable<OrderResponse> {
        return client.trackingOrder(orderId, token)
    }

    companion object {

        private var instance: ShippingRepositoryImpl? = null

        fun getInstance(): ShippingRepositoryImpl =
            instance ?: synchronized(this) {
                instance ?: ShippingRepositoryImpl().also {
                    instance = it
                }
            }
    }
}
