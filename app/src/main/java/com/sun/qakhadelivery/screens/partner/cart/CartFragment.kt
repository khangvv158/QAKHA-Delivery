package com.sun.qakhadelivery.screens.partner.cart

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.model.Cart
import com.sun.qakhadelivery.data.model.Partner
import com.sun.qakhadelivery.data.repository.CartRepositoryImpl
import com.sun.qakhadelivery.data.repository.TokenRepositoryImpl
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefsImpl
import com.sun.qakhadelivery.data.source.remote.schema.request.CartRequest
import com.sun.qakhadelivery.data.source.remote.schema.request.RemoveCartRequest
import com.sun.qakhadelivery.extensions.*
import com.sun.qakhadelivery.screens.checkout.CheckoutFragment
import com.sun.qakhadelivery.screens.partner.PartnerFragment.Companion.BUNDLE_PARTNER
import com.sun.qakhadelivery.screens.partner.cart.adapter.CartAdapter
import kotlinx.android.synthetic.main.fragment_cart.*

class CartFragment : BottomSheetDialogFragment(),
    CartContract.View {

    private val cartAdapter by lazy {
        CartAdapter()
    }
    private val presenter by lazy {
        CartPresenter(
            CartRepositoryImpl.getInstance(),
            TokenRepositoryImpl.getInstance(
                SharedPrefsImpl.getInstance(requireContext())
            )
        )
    }
    private var callbackCart: CallbackCart? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        handleEvent()
    }

    override fun onStart() {
        super.onStart()
        presenter.setView(this@CartFragment)
        arguments?.getParcelable<Partner>(BUNDLE_PARTNER)?.let {
            val products = it.categories
                .flatMap { category -> category.products }
                .toMutableList()
            presenter.getCart(it.id, products)
            cartAdapter.setOnClickCartListener(object :
                CartAdapter.OnClickCartListener.CartListener {

                override fun increase(cartRequest: CartRequest) {
                    presenter.updateCart(cartRequest, products)
                    disableInteraction()
                }

                override fun decrease(cartRequest: CartRequest) {
                    presenter.updateCart(cartRequest, products)
                    disableInteraction()
                }

                override fun remove(cartRequest: CartRequest) {
                    presenter.removeCart(
                        RemoveCartRequest(
                            cartRequest.partner_id,
                            cartRequest.product_id
                        ),
                        products
                    )
                    disableInteraction()
                }

                override fun finish() {
                    dismiss()
                }
            })
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        callbackCart?.onUpdateCart(cartAdapter.getCarts())
    }

    override fun onSuccessGetCart(carts: MutableList<Cart>) {
        cartAdapter.updateProducts(carts)
        enableInteraction()
    }

    override fun onSuccessUpdateCart(carts: MutableList<Cart>) {
        cartAdapter.updateProducts(carts)
        enableInteraction()
    }

    override fun onSuccessClearCart() {
        cartAdapter.clearProducts()
        callbackCart?.onClear()
        enableInteraction()
    }

    override fun onSuccessRemoveCart(carts: MutableList<Cart>) {
        cartAdapter.updateProducts(carts)
        enableInteraction()
        if (cartAdapter.mItems.isEmpty()) dismiss()
    }

    override fun onErrorGetCart(exception: String) {
        enableInteraction()
    }

    override fun onErrorUpdateCart(exception: String) {
        enableInteraction()
    }

    override fun onErrorRemoveCart(exception: String) {
        enableInteraction()
    }

    override fun onErrorClearCart(exception: String) {
        enableInteraction()
    }

    override fun onUpdateTotalPrice(total: Float) {
        totalTextView?.text = total.toString()
    }

    fun setOnCallback(callbackCart: CallbackCart) {
        this.callbackCart = callbackCart
    }

    private fun handleEvent() {
        cancelButton.setOnClickListener {
            dismiss()
        }
        clearButton.setOnClickListener {
            showAlertDialog {
                arguments?.getParcelable<Partner>(BUNDLE_PARTNER)?.let {
                    presenter.clearCart(it.id)
                }
            }
        }
        checkoutButton.setOnSafeClickListener {
            dismiss()
            parentFragment?.addFragmentBackStack(
                CheckoutFragment.newInstance(arguments),
                R.id.containerView
            )
        }
    }

    private fun initViews() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        cartRecyclerView.adapter = cartAdapter
    }

    private fun showAlertDialog(onAccept: (() -> Unit)) {
        DialogInterface.OnClickListener { _, type ->
            when (type) {
                DialogInterface.BUTTON_POSITIVE -> {
                    onAccept()
                }
            }
        }.also {
            AlertDialog.Builder(requireContext()).apply {
                setMessage(getString(R.string.messenger_clear_cart))
                    .setPositiveButton(getString(R.string.dialog_yes), it)
                    .setNegativeButton(getString(R.string.dialog_no), it)
            }.show()
        }
    }

    private fun disableInteraction() {
        loadingProgress?.show()
        checkoutButton?.disable()
    }

    private fun enableInteraction() {
        loadingProgress?.gone()
        checkoutButton?.enable()
    }

    interface CallbackCart {

        fun onUpdateCart(carts: MutableList<Cart>)

        fun onClear()
    }

    companion object {

        fun newInstance() = CartFragment()
    }
}
