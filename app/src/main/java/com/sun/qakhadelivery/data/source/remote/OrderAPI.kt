package com.sun.qakhadelivery.data.source.remote

import com.sun.qakhadelivery.data.model.Voucher
import com.sun.qakhadelivery.data.source.remote.schema.request.*
import com.sun.qakhadelivery.data.source.remote.schema.response.*
import com.sun.qakhadelivery.utils.Constants
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

interface OrderAPI {

    @GET("orders/vouchers")
    fun showVoucher(
        @Query("partner_id") partnerId: Int,
        @Header(Constants.AUTHORIZATION) token: String?
    ): Observable<MutableList<Voucher>>

    @POST("orders/voucher")
    fun applyVoucherTotal(
        @Body voucherTotal: VoucherTotal,
        @Header(Constants.AUTHORIZATION) token: String?
    ): Observable<ApplyVoucherResponse>

    @POST("orders/voucher")
    fun applyVoucherDistance(
        @Body voucherDistance: VoucherDistance,
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

    @POST("orders")
    fun createOrderWithVoucher(
        @Body orderVoucherRequest: OrderVoucherRequest,
        @Header(Constants.AUTHORIZATION) token: String?
    ): Observable<OrderResponse>

    @POST("orders/calc_distance")
    fun calculateDistance(
        @Body distanceRequest: DistanceRequest,
        @Header(Constants.AUTHORIZATION) token: String?
    ): Observable<DistanceResponse>
}
