package com.sun.qakhadelivery.screens.partner.tabs.product.adapter

import android.view.ViewGroup
import com.sun.qakhadelivery.data.model.Product
import com.sun.qakhadelivery.widget.recyclerview.CustomRecyclerView
import com.sun.qakhadelivery.widget.recyclerview.item.ProductItem
import com.sun.qakhadelivery.widget.recyclerview.viewholder.ProductViewHolder

class ProductAdapter : CustomRecyclerView.Adapter<ProductViewHolder>(mutableListOf()) {

    private var onClickAddToCart: ((Product) -> Unit)? = null
    private var onClickItemListener: ((Product) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomRecyclerView.ViewHolder<*> {
        return ProductViewHolder(parent).apply {
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

    fun updateProduct(products: List<Product>) {
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
