package com.sun.qakhadelivery.screens.checkout

import com.sun.qakhadelivery.data.model.*
import com.sun.qakhadelivery.data.source.remote.schema.request.ApplyVoucher
import com.sun.qakhadelivery.data.source.remote.schema.request.DistanceRequest
import com.sun.qakhadelivery.data.source.remote.schema.request.OrderRequest
import com.sun.qakhadelivery.data.source.remote.schema.request.VoucherCancel
import com.sun.qakhadelivery.data.source.remote.schema.response.ApplyVoucherResponse
import com.sun.qakhadelivery.data.source.remote.schema.response.CancelVoucherResponse
import com.sun.qakhadelivery.data.source.remote.schema.response.DistanceResponse
import com.sun.qakhadelivery.data.source.remote.schema.response.OrderResponse
import com.sun.qakhadelivery.utils.BasePresenter

interface CheckoutContract {

    interface View {

        fun onSuccessGetCart(carts: MutableList<Cart>)

        fun onErrorGetCart(exception: String)

        fun onSuccessApplyVoucher(applyVoucherResponse: ApplyVoucherResponse)

        fun onSuccessCancelVouchers(cancelVoucherResponse: CancelVoucherResponse)

        fun onSuccessGetVouchers(vouchers: MutableList<Voucher>)

        fun onUpdateTotalPrice(total: Float)

        fun onSuccessGetUser(user: User)

        fun onSuccessDistance(distanceResponse: DistanceResponse)

        fun onSuccessCreateOrder(orderResponse: OrderResponse)

        fun onErrorGetUser(exception: String)

        fun onErrorApplyVoucher(exception: String)

        fun onErrorGetVouchers(exception: String)

        fun onErrorCancelVouchers(exception: String)

        fun onErrorCalculatorDistance(exception: String)

        fun onErrorCreateOrder(exception: String)
    }

    interface Presenter : BasePresenter<View> {

        fun getCart(partnerId: Int, products: MutableList<Product>)

        fun applyVoucher(applyVoucher: ApplyVoucher)

        fun cancelVoucher(voucherCancel: VoucherCancel)

        fun getVouchers(partnerId: Int)

        fun getUserInfo()

        fun calculatorDistance(distanceRequest: DistanceRequest)

        fun createOrder(orderRequest: OrderRequest)
    }
}
