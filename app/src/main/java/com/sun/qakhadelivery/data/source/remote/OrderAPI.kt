package com.sun.qakhadelivery.data.source.remote

import com.sun.qakhadelivery.data.model.Voucher
import com.sun.qakhadelivery.data.source.remote.schema.request.ApplyVoucher
import com.sun.qakhadelivery.data.source.remote.schema.request.DistanceRequest
import com.sun.qakhadelivery.data.source.remote.schema.request.VoucherCancel
import com.sun.qakhadelivery.data.source.remote.schema.request.VoucherRequest
import com.sun.qakhadelivery.data.source.remote.schema.response.ApplyVoucherResponse
import com.sun.qakhadelivery.data.source.remote.schema.response.CancelVoucherResponse
import com.sun.qakhadelivery.utils.Constants
import com.sun.qakhadelivery.utils.Constants.DISTANCE
import com.sun.qakhadelivery.utils.Constants.ORDER_PATH
import com.sun.qakhadelivery.utils.Constants.VOUCHER
import com.sun.qakhadelivery.utils.Constants.VOUCHERS
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

interface OrderAPI {

    @GET(ORDER_PATH + VOUCHERS)
    fun showVoucher(
        @Body voucherRequest: VoucherRequest,
        @Header(Constants.AUTHORIZATION) token: String?
    ): Observable<MutableList<Voucher>>

    @POST(ORDER_PATH + VOUCHER)
    fun applyVoucher(
        @Body applyVoucher: ApplyVoucher,
        @Header(Constants.AUTHORIZATION) token: String?
    ): Observable<ApplyVoucherResponse>

    @HTTP(method = "DELETE", hasBody = true, path = ORDER_PATH + VOUCHER)
    fun cancelVoucher(
        @Body VoucherCancel: VoucherCancel,
        @Header(Constants.AUTHORIZATION) token: String?
    ): Observable<CancelVoucherResponse>

    //To do late
    @POST(ORDER_PATH + VOUCHER)
    fun createOrder(@Header(Constants.AUTHORIZATION) token: String?)

    @POST(ORDER_PATH + DISTANCE)
    fun calculateDistance(
        @Body distanceRequest: DistanceRequest,
        @Header(Constants.AUTHORIZATION) token: String?
    )
}
