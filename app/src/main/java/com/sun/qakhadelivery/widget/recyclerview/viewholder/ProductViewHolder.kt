package com.sun.qakhadelivery.widget.recyclerview.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.extensions.setOnSafeClickListener
import com.sun.qakhadelivery.widget.recyclerview.CustomRecyclerView
import com.sun.qakhadelivery.widget.recyclerview.item.ProductItem
import kotlinx.android.synthetic.main.item_product_partner.view.*

class ProductViewHolder(viewGroup: ViewGroup) :
    CustomRecyclerView.ViewHolder<ProductItem>(newInstance(viewGroup)) {

    override fun bind(item: ProductItem) {
        with(itemView) {
            Glide.with(itemView)
                .load(item.product.image.imageUrl)
                .placeholder(R.drawable.ic_placehoder_product)
                .into(productImageView)
            titleProductTextView.text = item.product.name
            describeProductTextView.text = item.product.description
            priceProductTextView.text = item.product.price.toString()
        }
    }

    fun setOnClickViewHolder(onAddToCart: (Int) -> Unit, onClickItem: (Int) -> Unit) {
        with(itemView) {
            addToCartButton.setOnSafeClickListener { onAddToCart(adapterPosition) }
            setOnSafeClickListener { onClickItem(adapterPosition) }
        }
    }

    companion object {
        fun newInstance(viewGroup: ViewGroup): View {
            return LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_product_partner, viewGroup, false)
        }
    }
}
