package com.sun.qakhadelivery.widget.recyclerview.viewholder

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.extensions.currencyVn
import com.sun.qakhadelivery.extensions.loadUrl
import com.sun.qakhadelivery.extensions.setOnSafeClickListener
import com.sun.qakhadelivery.widget.recyclerview.CustomRecyclerView
import com.sun.qakhadelivery.widget.recyclerview.item.ProductItem
import kotlinx.android.synthetic.main.item_product_partner.view.*

class ProductViewHolder(viewGroup: ViewGroup) :
    CustomRecyclerView.ViewHolder<ProductItem>(newInstance(viewGroup)) {

    @SuppressLint("ResourceType", "UseCompatLoadingForColorStateLists")
    override fun bind(item: ProductItem) {
        with(itemView) {
            productImageView.loadUrl(item.product.image.imageUrl)
            titleProductTextView.text = item.product.name
            describeProductTextView.text = item.product.description
            priceProductTextView.text = item.product.price.toString().currencyVn()
            if(!item.product.inStock()){
                addToCartButton.backgroundTintList = context.resources.getColorStateList(R.color.colorGrayBombay);
            }
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
