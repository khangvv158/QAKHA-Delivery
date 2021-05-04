package com.sun.qakhadelivery.widget.recyclerview.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.extensions.currencyVn
import com.sun.qakhadelivery.extensions.loadUrl
import com.sun.qakhadelivery.widget.recyclerview.CustomRecyclerView
import com.sun.qakhadelivery.widget.recyclerview.item.OrderItem
import kotlinx.android.synthetic.main.item_layout_bucket.view.*

class OrderViewHolder(viewGroup: ViewGroup) :
    CustomRecyclerView.ViewHolder<OrderItem>(newInstance(viewGroup)) {

    override fun bind(item: OrderItem) {
        with(itemView) {
            imageViewProduct?.loadUrl(
                item
                    .details
                    .product
                    .image
                    .imageUrl
            )
            textViewNameProduct?.text = item.details.product.name
            textViewQuantityProduct?.text = item.details.quantity.toString()
            textViewPriceProduct?.text = item.details.price.toString().currencyVn()
        }
    }

    companion object {

        fun newInstance(viewGroup: ViewGroup): View {
            return LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_layout_bucket, viewGroup, false)
        }
    }
}
