package com.sun.qakhadelivery.screens.partner

import com.sun.qakhadelivery.data.model.Cart
import com.sun.qakhadelivery.data.model.Product
import com.sun.qakhadelivery.data.source.remote.schema.request.CartRequest
import com.sun.qakhadelivery.utils.BasePresenter

interface PartnerContract {

    interface Presenter : BasePresenter<View> {

        fun getCart(partnerId: Int, products: MutableList<Product>)

        fun createCart(cartRequest: CartRequest, products: MutableList<Product>)

        fun updateCart(cartRequest: CartRequest, products: MutableList<Product>)
    }

    interface View {

        fun onSuccessGetCart(carts: MutableList<Cart>)

        fun onSuccessCreateCart(carts: MutableList<Cart>)

        fun onSuccessUpdateCart(carts: MutableList<Cart>)

        fun onErrorGetCart(exception: String)

        fun onErrorCreateCart(exception: String)

        fun onErrorUpdateCart(exception: String)
    }
}
