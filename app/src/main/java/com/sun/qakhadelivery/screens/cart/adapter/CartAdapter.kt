package com.sun.qakhadelivery.screens.cart.adapter

import android.view.ViewGroup
import com.sun.qakhadelivery.data.model.Cart
import com.sun.qakhadelivery.data.source.remote.schema.request.CartRequest
import com.sun.qakhadelivery.data.source.remote.schema.request.RemoveCartRequest
import com.sun.qakhadelivery.utils.Constants.DEFAULT_QUANTITY
import com.sun.qakhadelivery.widget.recyclerview.CustomRecyclerView
import com.sun.qakhadelivery.widget.recyclerview.item.CartItem
import com.sun.qakhadelivery.widget.recyclerview.viewholder.CartViewHolder

class CartAdapter : CustomRecyclerView.Adapter<CartViewHolder>(arrayListOf()) {

    private var listener: OnClickCartListener.CartListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomRecyclerView.ViewHolder<*> {
        return CartViewHolder(parent).apply {
            setOnClickListener(object : CartViewHolder.OnClickCartViewHolderListener {

                override fun increase(position: Int) {
                    getItems<CartItem>()[adapterPosition].cart.apply {
                        quantity += DEFAULT_QUANTITY
                        listener?.increase(CartRequest(product.id, quantity, partnerId))
                    }
                }

                override fun decrease(position: Int) {
                    getItems<CartItem>()[adapterPosition].cart.apply {
                        if (quantity > 1) {
                            quantity -= DEFAULT_QUANTITY
                            listener?.decrease(CartRequest(product.id, quantity, partnerId))
                        } else {
                            listener?.remove(RemoveCartRequest(product.id, partnerId))
                        }
                    }
                }
            })
        }
    }

    fun updateProducts(carts: MutableList<Cart>) {
        clearItems()
        addItems(carts.map {
            CartItem(it)
        })
    }

    fun clearProducts() {
        clearItems()
        if (mItems.isEmpty()) listener?.finish()
    }

    fun setOnClickCartListener(listener: OnClickCartListener.CartListener) {
        this.listener = listener
    }

    fun getCarts(): MutableList<Cart> {
        return getItems<CartItem>().map { it.cart }.toMutableList()
    }

    interface OnClickCartListener {

        interface CartListener {

            fun increase(cartRequest: CartRequest)

            fun decrease(cartRequest: CartRequest)

            fun remove(removeCartRequest: RemoveCartRequest)

            fun finish()
        }
    }
}
