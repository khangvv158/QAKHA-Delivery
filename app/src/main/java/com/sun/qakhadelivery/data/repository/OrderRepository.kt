package com.sun.qakhadelivery.data.repository

import com.sun.qakhadelivery.data.model.Voucher
import com.sun.qakhadelivery.data.source.remote.OrderAPI
import com.sun.qakhadelivery.data.source.remote.RetrofitClient
import com.sun.qakhadelivery.data.source.remote.schema.request.*
import com.sun.qakhadelivery.data.source.remote.schema.response.*
import io.reactivex.rxjava3.core.Observable

interface OrderRepository {

    fun showVoucher(partnerId: Int, token: String): Observable<MutableList<Voucher>>

    fun applyVoucherTotal(
        voucherTotal: VoucherTotal,
        token: String
    ): Observable<ApplyVoucherResponse>

    fun applyVoucherDistance(
        voucherDistance: VoucherDistance,
        token: String
    ): Observable<ApplyVoucherResponse>

    fun cancelVoucher(
        VoucherCancel: VoucherCancel,
        token: String
    ): Observable<CancelVoucherResponse>

    fun createOrder(orderRequest: OrderRequest, token: String): Observable<OrderResponse>

    fun createOrderWithVoucher(
        orderVoucherRequest: OrderVoucherRequest,
        token: String
    ): Observable<OrderResponse>

    fun calculateDistance(
        distanceRequest: DistanceRequest,
        token: String
    ): Observable<DistanceResponse>
}

class OrderRepositoryImpl private constructor() : OrderRepository {

    private val client = RetrofitClient.getInstance().create(OrderAPI::class.java)

    override fun showVoucher(
        partnerId: Int,
        token: String
    ): Observable<MutableList<Voucher>> {
        return client.showVoucher(partnerId, token)
    }

    override fun applyVoucherTotal(
        voucherTotal: VoucherTotal,
        token: String
    ): Observable<ApplyVoucherResponse> {
        return client.applyVoucherTotal(voucherTotal, token)
    }

    override fun applyVoucherDistance(
        voucherDistance: VoucherDistance,
        token: String
    ): Observable<ApplyVoucherResponse> {
        return client.applyVoucherDistance(voucherDistance, token)
    }

    override fun cancelVoucher(
        VoucherCancel: VoucherCancel,
        token: String
    ): Observable<CancelVoucherResponse> {
        return client.cancelVoucher(VoucherCancel, token)
    }

    override fun createOrder(orderRequest: OrderRequest, token: String): Observable<OrderResponse> {
        return client.createOrder(orderRequest, token)
    }

    override fun createOrderWithVoucher(
        orderVoucherRequest: OrderVoucherRequest,
        token: String
    ): Observable<OrderResponse> {
        return client.createOrderWithVoucher(orderVoucherRequest, token)
    }

    override fun calculateDistance(
        distanceRequest: DistanceRequest,
        token: String
    ): Observable<DistanceResponse> {
        return client.calculateDistance(distanceRequest, token)
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
