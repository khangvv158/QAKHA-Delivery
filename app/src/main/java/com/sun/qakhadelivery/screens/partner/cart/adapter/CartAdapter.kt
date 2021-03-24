package com.sun.qakhadelivery.screens.partner.cart.adapter

import android.view.ViewGroup
import com.sun.qakhadelivery.data.model.Cart
import com.sun.qakhadelivery.data.model.Product
import com.sun.qakhadelivery.widget.recyclerview.CustomRecyclerView
import com.sun.qakhadelivery.widget.recyclerview.item.CartItem
import com.sun.qakhadelivery.widget.recyclerview.viewholder.CartViewHolder

class CartAdapter : CustomRecyclerView.Adapter<CartViewHolder>(arrayListOf()),
    CartViewHolder.OnClickCartViewHolderListener {

    private var listener: OnClickCartListener.Cart? = null
    private var changeListener: OnClickCartListener.ChangeListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomRecyclerView.ViewHolder<*> {
        return CartViewHolder(parent).apply {
            setOnClickListener(this@CartAdapter)
        }
    }

    override fun increase(position: Int, callback: (Cart) -> Unit) {
        getItemPosition<CartItem>(position)?.cart?.also {
            addProduct(it.product)
            callback(it)
            listener?.increase()
        }
    }

    override fun decrease(position: Int, callback: (Cart) -> Unit) {
        getItemPosition<CartItem>(position)?.cart?.also {
            removeProduct(it.product)
            callback(it)
            listener?.decrease()
        }
        if (mItems.isEmpty()) {
            listener?.finish()
        }
    }

    fun removeProduct(product: Product) {
        getItems<CartItem>().forEachIndexed { index: Int, it: CartItem ->
            if (it.cart.product == product && it.cart.amount > 1) {
                it.cart.amount--
                notifyItemChanged(index)
            } else if (it.cart.product.id == product.id && it.cart.amount == 1) {
                removeItem(it)
            }
        }
        changeListener?.totalListener(calculatorTotal(), calculatorItems())
    }

    fun updateProducts(carts: MutableList<Cart>) {
        mItems.clear()
        addItems(carts.map {
            CartItem(it)
        })
        changeListener?.totalListener(calculatorTotal(), calculatorItems())
    }

    fun addProduct(product: Product) {
        if (mItems.isEmpty()) {
            addItem(CartItem(Cart(product, 1)))
        } else {
            containsCart(product).also {
                if (it != null) {
                    val index = getItemPosition(it)
                    it.cart.amount++
                    notifyItemChanged(index)
                } else {
                    addItem(CartItem(Cart(product, 1)))
                }
            }
        }
        changeListener?.totalListener(calculatorTotal(), calculatorItems())
    }

    fun clearProducts() {
        clearItems()
        if (mItems.isEmpty()) {
            listener?.finish()
        }
        changeListener?.totalListener(calculatorTotal(), calculatorItems())
    }

    private fun containsCart(product: Product): CartItem? {
        getItems<CartItem>().forEach {
            if (it.cart.product == product) {
                return it
            }
        }
        return null
    }

    fun calculatorTotal(): Float {
        var total = 0f
        getItems<CartItem>().forEach {
            total += it.cart.amount * it.cart.product.price
        }
        return total
    }

    private fun calculatorItems(): Int {
        var items = 0
        getItems<CartItem>().forEach {
            items += it.cart.amount
        }
        return items
    }

    fun setOnChangeListener(changeListener: OnClickCartListener.ChangeListener) {
        this.changeListener = changeListener
    }

    fun setOnClickCartListener(listener: OnClickCartListener.Cart) {
        this.listener = listener
    }

    abstract class OnClickCartListener {

        interface Cart {

            fun increase()

            fun decrease()

            fun finish()
        }

        interface ChangeListener {

            fun totalListener(total: Float, counter: Int)
        }
    }
}
