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
import com.sun.qakhadelivery.screens.signin.SignInFragment
import com.sun.qakhadelivery.utils.UserUtils
import com.sun.qakhadelivery.utils.addFragmentSlideAnim
import kotlinx.android.synthetic.main.fragment_product_order.*

class ProductPartnerFragment : Fragment(), ProductPartnerContract.View {

    private val adapter by lazy { CategoriesAdapter() }

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
    }

    companion object {
        const val BUNDLE_PRODUCT = "EXTRA_PRODUCT"
        const val KEY_REQUEST_PARTNER = "KEY_REQUEST_PARTNER"

        fun newInstance() = ProductPartnerFragment()
    }
}
