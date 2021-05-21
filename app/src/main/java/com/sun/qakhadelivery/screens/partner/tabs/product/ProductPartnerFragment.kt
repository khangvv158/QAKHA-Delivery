package com.sun.qakhadelivery.screens.partner.tabs.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.model.Partner
import com.sun.qakhadelivery.extensions.addFragmentSlideAnim
import com.sun.qakhadelivery.screens.partner.tabs.product.adapter.CategoriesAdapter
import com.sun.qakhadelivery.screens.product.ProductFragment
import com.sun.qakhadelivery.screens.signin.SignInFragment
import com.sun.qakhadelivery.utils.BasePageFragment
import com.sun.qakhadelivery.utils.UserUtils
import kotlinx.android.synthetic.main.fragment_product_order.*

class ProductPartnerFragment : BasePageFragment(), ProductPartnerContract.View {

    private val categoriesAdapter by lazy { CategoriesAdapter() }

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

    override fun fetchData() {
        initData()
    }

    private fun initViews() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        categoryRecyclerView.apply {
            adapter = categoriesAdapter.apply {
                setHasStableIds(true)
            }
            setHasFixedSize(true)
        }
    }

    private fun initData() {
        arguments?.getParcelable<Partner>(BUNDLE_PARTNER)?.let {
            categoriesAdapter.updateCategory(it.categories)
        }
    }

    private fun handleEvent() {
        categoriesAdapter.setOnClickAddToCart {
            UserUtils.workWithSignIn(requireContext(), {
                parentFragment?.setFragmentResult(KEY_REQUEST_PARTNER,
                    Bundle().apply {
                        putParcelable(BUNDLE_PRODUCT, it)
                    })
            }, {
                parentFragment?.addFragmentSlideAnim(
                    SignInFragment.newInstance(),
                    R.id.containerView
                )
            })
        }
        categoriesAdapter.setOnClickItemRecyclerView {
            parentFragment?.addFragmentSlideAnim(
                ProductFragment.newInstance(it),
                R.id.containerView
            )
        }
    }

    companion object {
        const val BUNDLE_PRODUCT = "BUNDLE_PRODUCT"
        const val BUNDLE_PARTNER = "BUNDLE_PARTNER"
        const val KEY_REQUEST_PARTNER = "KEY_REQUEST_PARTNER"

        fun newInstance(partner: Partner?) = ProductPartnerFragment().apply {
            arguments = Bundle().apply {
                putParcelable(BUNDLE_PARTNER, partner)
            }
        }
    }
}
