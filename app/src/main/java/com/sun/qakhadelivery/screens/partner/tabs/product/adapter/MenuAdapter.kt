package com.sun.qakhadelivery.screens.partner.tabs.product.adapter

import android.view.ViewGroup
import com.sun.qakhadelivery.data.model.Product
import com.sun.qakhadelivery.widget.recyclerview.CustomRecyclerView
import com.sun.qakhadelivery.widget.recyclerview.item.ProductItem
import com.sun.qakhadelivery.widget.recyclerview.viewholder.MenuViewHolder

class MenuAdapter : CustomRecyclerView.Adapter<MenuViewHolder>(mutableListOf()) {

    private var onClickAddToCart: ((Product) -> Unit)? = null
    private var onClickItemListener: ((Product) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomRecyclerView.ViewHolder<*> {
        return MenuViewHolder(parent).apply {
            setOnClickViewHolder({
                onClickAddToCart?.let { func ->
                    func(getItems<ProductItem>()[it].product)
                }
            }, {
                onClickItemListener?.let { func ->
                    func(getItems<ProductItem>()[it].product)
                }
            })
        }
    }

    fun updateMenu(products: List<Product>) {
        addItems(products.map {
            ProductItem(it)
        })
    }

    fun setOnClickAddToCart(listener: (Product) -> Unit) {
        onClickAddToCart = listener
    }

    fun setOnClickItemRecyclerView(listener: (Product) -> Unit) {
        onClickItemListener = listener
    }
}
