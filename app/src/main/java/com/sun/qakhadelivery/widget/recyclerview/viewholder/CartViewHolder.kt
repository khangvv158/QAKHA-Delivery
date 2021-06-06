package com.sun.qakhadelivery.widget.recyclerview.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.extensions.currencyVn
import com.sun.qakhadelivery.extensions.loadUrl
import com.sun.qakhadelivery.extensions.setOnSafeClickListener
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
            priceProductTextView.text = item.cart.product.price.toString().currencyVn()
            quantityTextView.text = item.cart.quantity.toString()
        }
    }

    fun setOnClickListener(listener: OnClickCartViewHolderListener) {
        with(itemView) {
            increaseButton.setOnSafeClickListener {
                listener.increase(adapterPosition)
            }
            decreaseButton.setOnSafeClickListener {
                listener.decrease(adapterPosition)
            }
        }
    }

    interface OnClickCartViewHolderListener {

        fun increase(position: Int)

        fun decrease(position: Int)
    }

    companion object {

        fun newInstance(viewGroup: ViewGroup): View {
            return LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_product_cart, viewGroup, false)
        }
    }
}
