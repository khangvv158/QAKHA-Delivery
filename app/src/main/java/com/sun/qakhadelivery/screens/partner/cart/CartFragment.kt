package com.sun.qakhadelivery.screens.partner.cart

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.model.Product
import com.sun.qakhadelivery.screens.partner.cart.adapter.CartAdapter
import kotlinx.android.synthetic.main.fragment_cart.*

class CartFragment : BottomSheetDialogFragment(),
    CartAdapter.OnClickCartListener.ChangeListener {

    private val cartAdapter by lazy {
        CartAdapter().apply {
            setOnChangeListener(this@CartFragment)
        }
    }
    private var counterListener: ((Int) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initData()
        handleEvent()
    }

    override fun totalListener(total: Float, counter: Int) {
        totalTextView?.text = total.toString()
        counterListener?.let { func -> func(counter) }
    }

    fun addProduct(product: Product) {
        cartAdapter.addProduct(product)
    }

    fun setOnCountListener(counter: (Int) -> Unit) {
        counterListener = counter
    }

    private fun handleEvent() {
        cartAdapter.setOnClickCartListener(object : CartAdapter.OnClickCartListener.Cart {

            override fun increase() = Unit

            override fun decrease() = Unit

            override fun finish() {
                dismiss()
            }
        })
        cancelButton.setOnClickListener {
            dismiss()
        }
        clearButton.setOnClickListener {
            showAlertDialog {
                cartAdapter.clearProducts()
            }
        }
    }

    private fun initData() {
        totalTextView.text = cartAdapter.calculatorTotal().toString()
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

    companion object {

        fun newInstance() = CartFragment()
    }
}
