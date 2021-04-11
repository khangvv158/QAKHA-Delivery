package com.sun.qakhadelivery.screens.partner.cart.adapter

import android.view.ViewGroup
import com.sun.qakhadelivery.data.model.Cart
import com.sun.qakhadelivery.data.source.remote.schema.request.CartRequest
import com.sun.qakhadelivery.widget.recyclerview.CustomRecyclerView
import com.sun.qakhadelivery.widget.recyclerview.item.CartItem
import com.sun.qakhadelivery.widget.recyclerview.viewholder.CartViewHolder

class CartAdapter :
    CustomRecyclerView.Adapter<CartViewHolder>(arrayListOf()),
    CartViewHolder.OnClickCartViewHolderListener {

    private var listener: OnClickCartListener.CartListener? = null
    private var calculatorListener: OnClickCartListener.CalculatorListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomRecyclerView.ViewHolder<*> {
        return CartViewHolder(parent).apply {
            setOnClickListener(getItems<CartItem>().toMutableList(), this@CartAdapter)
        }
    }

    override fun increase(cartRequest: CartRequest) {
        listener?.increase(cartRequest)
    }

    override fun decrease(cartRequest: CartRequest) {
        listener?.decrease(cartRequest)
        if (mItems.isEmpty()) listener?.finish()
    }

    override fun remove(cartRequest: CartRequest) {
        listener?.remove(cartRequest)
    }

    fun updateProducts(carts: MutableList<Cart>) {
        clearItems()
        addItems(carts.map {
            CartItem(it)
        })
        if (mItems.isEmpty()) listener?.finish()
        calculatorListener?.setOnListener(geSubtotalPrice())
    }

    fun clearProducts() {
        clearItems()
        if (mItems.isEmpty()) listener?.finish()
        calculatorListener?.setOnListener(geSubtotalPrice())
    }

    fun setOnChangeListener(listener: OnClickCartListener.CalculatorListener) {
        calculatorListener = listener
    }

    fun setOnClickCartListener(listener: OnClickCartListener.CartListener) {
        this.listener = listener
    }

    fun getCarts(): MutableList<Cart> {
        return getItems<CartItem>().map { it.cart }.toMutableList()
    }

    private fun geSubtotalPrice(): Float {
        return getItems<CartItem>().map { it.cart.quantity * it.cart.product.price }.sum()
    }

    interface OnClickCartListener {

        interface CartListener {

            fun increase(cartRequest: CartRequest)

            fun decrease(cartRequest: CartRequest)

            fun remove(cartRequest: CartRequest)

            fun finish()
        }

        interface CalculatorListener {

            fun setOnListener(total: Float)
        }
    }
}
