package com.sun.qakhadelivery.screens.partner.tabs.product.adapter

import android.view.ViewGroup
import com.sun.qakhadelivery.data.model.Category
import com.sun.qakhadelivery.data.model.Product
import com.sun.qakhadelivery.widget.recyclerview.CustomRecyclerView
import com.sun.qakhadelivery.widget.recyclerview.item.CategoryItem
import com.sun.qakhadelivery.widget.recyclerview.viewholder.CategoriesViewHolder

class CategoriesAdapter : CustomRecyclerView.Adapter<CategoriesViewHolder>(mutableListOf()) {

    private var onClickAddToCart: ((Product) -> Unit)? = null
    private var onClickItemListener: ((Product) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomRecyclerView.ViewHolder<*> {
        return CategoriesViewHolder(parent).apply {
            initProductAdapter(
                onClickAddToCart,
                onClickItemListener
            )
        }
    }

    fun updateCategory(categories: MutableList<Category>) {
        categories.map { CategoryItem(it) }.also {
            addItems(it)
        }
    }

    fun setOnClickAddToCart(listener: (Product) -> Unit) {
        onClickAddToCart = listener
    }

    fun setOnClickItemRecyclerView(listener: (Product) -> Unit) {
        onClickItemListener = listener
    }
}
