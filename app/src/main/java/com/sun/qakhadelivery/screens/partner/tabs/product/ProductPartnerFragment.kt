package com.sun.qakhadelivery.screens.partner.tabs.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.model.Product
import com.sun.qakhadelivery.screens.partner.tabs.product.adapter.MenuAdapter
import kotlinx.android.synthetic.main.fragment_product_order.*

class ProductPartnerFragment : Fragment(), ProductPartnerContract.View {

    private val adapter by lazy { MenuAdapter() }
    private val presenter by lazy { ProductPartnerPresenter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        handleEvent()
    }

    override fun onStart() {
        super.onStart()
        presenter.run {
            setView(this@ProductPartnerFragment)
            getMenus()
        }
    }

    override fun onGetMenusSuccess(products: MutableList<Product>) {
        adapter.updateMenu(products)
    }

    private fun initViews() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        productPartnerRecyclerView.adapter = adapter
    }

    private fun handleEvent() {
        adapter.setOnClickAddToCart {
            parentFragmentManager.setFragmentResult(
                KEY_REQUEST,
                Bundle().apply {
                    putParcelable(EXTRA_PRODUCT, it)
                }
            )
        }
    }

    companion object {
        const val KEY_REQUEST = "KEY_REQUEST"
        const val EXTRA_PRODUCT = "EXTRA_PRODUCT"

        fun newInstance() = ProductPartnerFragment()
    }
}
