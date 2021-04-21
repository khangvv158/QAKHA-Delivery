package com.sun.qakhadelivery.data.source.remote

import com.sun.qakhadelivery.data.model.Voucher
import com.sun.qakhadelivery.data.source.remote.schema.request.ApplyVoucher
import com.sun.qakhadelivery.data.source.remote.schema.request.DistanceRequest
import com.sun.qakhadelivery.data.source.remote.schema.request.OrderRequest
import com.sun.qakhadelivery.data.source.remote.schema.request.VoucherCancel
import com.sun.qakhadelivery.data.source.remote.schema.response.ApplyVoucherResponse
import com.sun.qakhadelivery.data.source.remote.schema.response.CancelVoucherResponse
import com.sun.qakhadelivery.data.source.remote.schema.response.DistanceResponse
import com.sun.qakhadelivery.data.source.remote.schema.response.OrderResponse
import com.sun.qakhadelivery.utils.Constants
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

interface OrderAPI {

    @GET("orders/vouchers")
    fun showVoucher(
        @Query(Constants.PARTNER_ID) partnerId: Int,
        @Header(Constants.AUTHORIZATION) token: String?
    ): Observable<MutableList<Voucher>>

    @POST("orders/voucher")
    fun applyVoucher(
        @Body applyVoucher: ApplyVoucher,
        @Header(Constants.AUTHORIZATION) token: String?
    ): Observable<ApplyVoucherResponse>

    @HTTP(method = "DELETE", hasBody = true, path = "orders/voucher")
    fun cancelVoucher(
        @Body VoucherCancel: VoucherCancel,
        @Header(Constants.AUTHORIZATION) token: String?
    ): Observable<CancelVoucherResponse>

    @POST("orders")
    fun createOrder(
        @Body orderRequest: OrderRequest,
        @Header(Constants.AUTHORIZATION) token: String?
    ): Observable<OrderResponse>

    @POST("orders/calc_distance")
    fun calculateDistance(
        @Body distanceRequest: DistanceRequest,
        @Header(Constants.AUTHORIZATION) token: String?
    ): Observable<DistanceResponse>
}
