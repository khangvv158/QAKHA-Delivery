package com.sun.qakhadelivery.screens.checkout

import com.sun.qakhadelivery.data.model.Cart
import com.sun.qakhadelivery.data.model.Product
import com.sun.qakhadelivery.data.source.remote.schema.request.ApplyVoucher
import com.sun.qakhadelivery.data.source.remote.schema.request.DistanceRequest
import com.sun.qakhadelivery.data.source.remote.schema.response.ApplyVoucherResponse
import com.sun.qakhadelivery.utils.BasePresenter

interface CheckoutContract {

    interface View {

        fun onSuccessGetCart(carts: MutableList<Cart>)

        fun onErrorGetCart(exception: String)

        fun onSuccessApplyVoucher(applyVoucherResponse: ApplyVoucherResponse)

        fun onErrorApplyVoucher(exception: String)

        fun onUpdateTotalPrice(total: Float)
    }

    interface Presenter : BasePresenter<View> {

        fun getCart(partnerId: Int, products: MutableList<Product>)

        fun applyVoucher(applyVoucher: ApplyVoucher)

        fun calculatorPrice(distanceRequest: DistanceRequest)
    }
}
