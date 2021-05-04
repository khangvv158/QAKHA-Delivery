package com.sun.qakhadelivery.screens.cart

import com.sun.qakhadelivery.data.model.Cart
import com.sun.qakhadelivery.data.model.Product
import com.sun.qakhadelivery.data.source.remote.schema.request.CartRequest
import com.sun.qakhadelivery.data.source.remote.schema.request.RemoveCartRequest
import com.sun.qakhadelivery.utils.BasePresenter

interface CartContract {

    interface Presenter : BasePresenter<View> {

        fun getCart(partnerId: Int, products: MutableList<Product>)

        fun createCart(cartRequest: CartRequest, products: MutableList<Product>)

        fun updateCart(cartRequest: CartRequest, products: MutableList<Product>)

        fun removeCart(removeCartRequest: RemoveCartRequest, products: MutableList<Product>)

        fun clearCart(partnerId: Int)

        fun checkCartEmpty(partnerId: Int)
    }

    interface View {

        fun onSuccessGetCart(carts: MutableList<Cart>)

        fun onSuccessUpdateCart(carts: MutableList<Cart>)

        fun onSuccessClearCart()

        fun onSuccessRemoveCart(carts: MutableList<Cart>)

        fun onErrorGetCart(exception: String)

        fun onErrorUpdateCart(exception: String)

        fun onErrorRemoveCart(exception: String)

        fun onErrorClearCart(exception: String)

        fun onUpdateTotalPrice(total: Float)

        fun onCartNotEmpty()

        fun onCartEmpty()

        fun onErrorCheckEmpty(exception: String)
    }
}
