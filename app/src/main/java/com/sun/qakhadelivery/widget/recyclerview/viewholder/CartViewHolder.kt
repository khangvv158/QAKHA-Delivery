package com.sun.qakhadelivery.widget.recyclerview.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.source.remote.schema.request.CartRequest
import com.sun.qakhadelivery.data.source.remote.schema.request.RemoveCartRequest
import com.sun.qakhadelivery.extensions.loadUrl
import com.sun.qakhadelivery.utils.Constants.DEFAULT_QUANTITY
import com.sun.qakhadelivery.widget.recyclerview.CustomRecyclerView
import com.sun.qakhadelivery.widget.recyclerview.item.CartItem
import kotlinx.android.synthetic.main.item_product_cart.view.*

class CartViewHolder(viewGroup: ViewGroup) :
    CustomRecyclerView.ViewHolder<CartItem>(newInstance(viewGroup)) {

    override fun bind(item: CartItem) {
        with(itemView) {
            productImageView.loadUrl(item.cart.product.image.imageUrl)
            titleProductTextView.text = item.cart.product.name
            describeProductTextView.text = item.cart.product.description
            priceProductTextView.text = item.cart.product.price.toString()
            quantityTextView.text = item.cart.quantity.toString()
        }
    }

    fun setOnClickListener(
        cartItems: MutableList<CartItem>,
        listener: OnClickCartViewHolderListener
    ) {
        with(itemView) {
            increaseButton.setOnClickListener {
                cartItems[adapterPosition].cart.apply {
                    quantity += DEFAULT_QUANTITY
                    listener.increase(CartRequest(product.id, quantity, partnerId))
                }
            }
            decreaseButton.setOnClickListener {
                cartItems[adapterPosition].cart.apply {
                    if (quantity > 1) {
                        quantity -= DEFAULT_QUANTITY
                        listener.decrease(CartRequest(product.id, quantity, partnerId))
                    } else {
                        listener.remove(RemoveCartRequest(product.id, partnerId))
                    }
                }
            }
        }
    }

    interface OnClickCartViewHolderListener {

        fun increase(cartRequest: CartRequest)

        fun decrease(cartRequest: CartRequest)

        fun remove(removeCartRequest: RemoveCartRequest)
    }

    companion object {

        fun newInstance(viewGroup: ViewGroup): View {
            return LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_product_cart, viewGroup, false)
        }
    }
}
