package com.sun.qakhadelivery.screens.partner.tabs.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.model.Partner
import com.sun.qakhadelivery.screens.home.HomeFragment.Companion.BUNDLE_PARTNER
import com.sun.qakhadelivery.screens.partner.tabs.product.adapter.CategoriesAdapter
import kotlinx.android.synthetic.main.fragment_product_order.*

class ProductPartnerFragment : Fragment(), ProductPartnerContract.View {

    private val adapter by lazy { CategoriesAdapter() }
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
        initData()
        handleEvent()
    }

    override fun onStart() {
        super.onStart()
        presenter.setView(this@ProductPartnerFragment)
    }

    private fun initViews() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        categoryRecyclerView.adapter = adapter
    }

    private fun initData() {
        arguments?.getParcelable<Partner>(BUNDLE_PARTNER)?.let {
            adapter.updateCategory(it.categories)
        }
    }

    private fun handleEvent() {
        adapter.setOnClickAddToCart {
            parentFragment?.setFragmentResult(
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
