package com.sun.qakhadelivery.widget.recyclerview.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.model.Cart
import com.sun.qakhadelivery.widget.recyclerview.CustomRecyclerView
import com.sun.qakhadelivery.widget.recyclerview.item.CartItem
import kotlinx.android.synthetic.main.item_product_cart.view.*

class CartViewHolder(viewGroup: ViewGroup) :
    CustomRecyclerView.ViewHolder<CartItem>(newInstance(viewGroup)) {

    override fun bind(item: CartItem) {
        with(itemView) {
            titleProductTextView.text = item.cart.product.name
            describeProductTextView.text = item.cart.product.description
            priceProductTextView.text = item.cart.product.price.toString()
            amountTextView.text = item.cart.amount.toString()
        }
    }

    fun setOnClickListener(listener: OnClickCartViewHolderListener) {
        with(itemView) {
            increaseButton.setOnClickListener {
                listener.increase(adapterPosition) { cart ->
                    amountTextView.text = cart.amount.toString()
                }
            }
            decreaseButton.setOnClickListener {
                listener.decrease(adapterPosition) { cart ->
                    amountTextView.text = cart.amount.toString()
                }
            }
        }
    }

    interface OnClickCartViewHolderListener {

        fun increase(position: Int, callback: (Cart) -> Unit)

        fun decrease(position: Int, callback: (Cart) -> Unit)
    }

    companion object {
        fun newInstance(viewGroup: ViewGroup): View {
            return LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_product_cart, viewGroup, false)
        }
    }
}
