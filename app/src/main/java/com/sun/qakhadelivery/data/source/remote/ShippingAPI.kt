package com.sun.qakhadelivery.data.source.remote

import com.sun.qakhadelivery.data.source.remote.schema.response.HistoryResponse
import com.sun.qakhadelivery.data.source.remote.schema.response.OrderResponse
import com.sun.qakhadelivery.utils.Constants
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ShippingAPI {

    @GET("users/orders_shipping")
    fun getShipping(
        @Header(Constants.AUTHORIZATION) token: String?
    ): Observable<MutableList<HistoryResponse>>

    @GET("users/tracking_order")
    fun trackingOrder(
        @Query("order_id") orderId: Int,
        @Header(Constants.AUTHORIZATION) token: String?
    ): Observable<OrderResponse>
}
