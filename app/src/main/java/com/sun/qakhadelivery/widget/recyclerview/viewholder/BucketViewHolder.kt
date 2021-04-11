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
            imageViewProduct?.loadUrl("https://i.pinimg.com/564x/0a/e3/72/0ae37284e96d4740527887f1b051bb8f.jpg")
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
