package com.sun.qakhadelivery.widget.recyclerview.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.extensions.loadUrl
import com.sun.qakhadelivery.widget.recyclerview.CustomRecyclerView
import com.sun.qakhadelivery.widget.recyclerview.item.BucketItem
import kotlinx.android.synthetic.main.item_layout_bucket.view.*

class BucketViewHolder(viewGroup: ViewGroup) :
    CustomRecyclerView.ViewHolder<BucketItem>(newInstance(viewGroup)) {

    override fun bind(item: BucketItem) {
        with(itemView) {
            imageViewProduct?.loadUrl(item.cart.product.image.imageUrl)
            textViewNameProduct?.text = item.cart.product.name
            textViewQuantityProduct?.text = item.cart.quantity.toString()
            textViewPriceProduct?.text = item.cart.product.price.toString()
        }
    }

    companion object {

        fun newInstance(viewGroup: ViewGroup): View {
            return LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_layout_bucket, viewGroup, false)
        }
    }
}
