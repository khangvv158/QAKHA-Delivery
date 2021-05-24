package com.sun.qakhadelivery.widget.recyclerview.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.model.Product
import com.sun.qakhadelivery.screens.partner.tabs.product.adapter.ProductAdapter
import com.sun.qakhadelivery.widget.recyclerview.CustomRecyclerView
import com.sun.qakhadelivery.widget.recyclerview.divider.DividerItemDecorator
import com.sun.qakhadelivery.widget.recyclerview.item.CategoryItem
import kotlinx.android.synthetic.main.item_layout_category.view.*


class CategoriesViewHolder(viewGroup: ViewGroup) :
    CustomRecyclerView.ViewHolder<CategoryItem>(newInstance(viewGroup)) {

    private val productAdapter = ProductAdapter()

    override fun bind(item: CategoryItem) {
        itemView.categoryTextView.text = item.category.name
        productAdapter.updateProduct(item.category.products)
    }

    fun initProductAdapter(
        cartListener: ((Product) -> Unit)?,
        clickListener: ((Product) -> Unit)?
    ) {
        with(itemView) {
            productRecyclerView.apply {
                adapter = productAdapter.apply {
                    cartListener?.let { setOnClickAddToCart(it) }
                    clickListener?.let { setOnClickItemRecyclerView(it) }
                }
                ContextCompat.getDrawable(context, R.drawable.divider)?.let {
                    addItemDecoration(DividerItemDecorator(it))
                }
                setHasFixedSize(true)
                setRecycledViewPool(RecyclerView.RecycledViewPool())
            }
        }
    }

    companion object {

        fun newInstance(viewGroup: ViewGroup): View {
            return LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_layout_category, viewGroup, false)
        }
    }
}
