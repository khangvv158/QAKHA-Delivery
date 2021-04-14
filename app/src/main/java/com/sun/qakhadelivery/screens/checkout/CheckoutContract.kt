package com.sun.qakhadelivery.screens.checkout

import com.sun.qakhadelivery.data.model.Cart
import com.sun.qakhadelivery.data.model.Product
import com.sun.qakhadelivery.utils.BasePresenter

interface CheckoutContract {

    interface View {

        fun onSuccessGetCart(carts: MutableList<Cart>)

        fun onErrorGetCart(exception: String)

        fun onUpdateTotalPrice(total: Float)
    }

    interface Presenter : BasePresenter<View> {

        fun getCart(partnerId: Int, products: MutableList<Product>)
    }
}
