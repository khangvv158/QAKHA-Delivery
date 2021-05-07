package com.sun.qakhadelivery.screens.checkout

import com.sun.qakhadelivery.data.model.Cart
import com.sun.qakhadelivery.data.model.Product
import com.sun.qakhadelivery.data.model.User
import com.sun.qakhadelivery.data.model.Voucher
import com.sun.qakhadelivery.data.source.remote.schema.request.*
import com.sun.qakhadelivery.data.source.remote.schema.response.*
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

        fun onSuccessCreateOrderWithVoucher(orderResponse: OrderResponse)

        fun onErrorGetUser(exception: String)

        fun onErrorApplyVoucher(exception: String)

        fun onErrorGetVouchers(exception: String)

        fun onErrorCancelVouchers(exception: String)

        fun onErrorCalculatorDistance(exception: String)

        fun onErrorCreateOrder(exception: String)

        fun onErrorCreateOrderWithVoucher(exception: String)
    }

    interface Presenter : BasePresenter<View> {

        fun getCart(partnerId: Int, products: MutableList<Product>)

        fun applyVoucherTotal(voucherTotal: VoucherTotal)

        fun cancelVoucher(voucherCancel: VoucherCancel)

        fun getVouchers(partnerId: Int)

        fun getUserInfo()

        fun calculatorDistance(distanceRequest: DistanceRequest)

        fun createOrder(orderRequest: OrderRequest)

        fun createOrderWithVoucher(orderVoucherRequest: OrderVoucherRequest)

        fun applyVoucherDistance(voucherDistance: VoucherDistance)
    }
}
