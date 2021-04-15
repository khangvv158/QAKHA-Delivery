package com.sun.qakhadelivery.data.repository

import com.sun.qakhadelivery.data.model.Voucher
import com.sun.qakhadelivery.data.source.remote.OrderAPI
import com.sun.qakhadelivery.data.source.remote.RetrofitClient
import com.sun.qakhadelivery.data.source.remote.schema.request.ApplyVoucher
import com.sun.qakhadelivery.data.source.remote.schema.request.DistanceRequest
import com.sun.qakhadelivery.data.source.remote.schema.request.VoucherCancel
import com.sun.qakhadelivery.data.source.remote.schema.request.VoucherRequest
import com.sun.qakhadelivery.data.source.remote.schema.response.CancelVoucherResponse
import com.sun.qakhadelivery.data.source.remote.schema.response.ApplyVoucherResponse
import io.reactivex.rxjava3.core.Observable

interface OrderRepository {

    fun showVoucher(voucherRequest: VoucherRequest, token: String): Observable<MutableList<Voucher>>

    fun applyVoucher(apply: ApplyVoucher, token: String): Observable<ApplyVoucherResponse>

    fun cancelVoucher(VoucherCancel: VoucherCancel, token: String): Observable<CancelVoucherResponse>

    fun createOrder()

    fun calculateDistance(distanceRequest: DistanceRequest, token: String)
}

class OrderRepositoryImpl private constructor() : OrderRepository {

    private val client = RetrofitClient.getInstance().create(OrderAPI::class.java)

    override fun showVoucher(
        voucherRequest: VoucherRequest,
        token: String
    ): Observable<MutableList<Voucher>> {
        return client.showVoucher(voucherRequest, token)
    }

    override fun applyVoucher(
        apply: ApplyVoucher,
        token: String
    ): Observable<ApplyVoucherResponse> {
        return client.applyVoucher(apply, token)
    }

    override fun cancelVoucher(
        VoucherCancel: VoucherCancel,
        token: String
    ): Observable<CancelVoucherResponse> {
        return client.cancelVoucher(VoucherCancel, token)
    }

    override fun createOrder() {
    }

    override fun calculateDistance(distanceRequest: DistanceRequest, token: String) {
        client.calculateDistance(distanceRequest, token)
    }

    companion object {

        private var instance: OrderRepositoryImpl? = null

        fun getInstance(): OrderRepositoryImpl =
            instance ?: synchronized(this) {
                instance ?: OrderRepositoryImpl().also {
                    instance = it
                }
            }
    }
}
