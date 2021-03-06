package com.sun.qakhadelivery.screens.partner.tabs.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.model.Event
import com.sun.qakhadelivery.data.model.Partner
import com.sun.qakhadelivery.extensions.addFragmentSlideAnim
import com.sun.qakhadelivery.screens.partner.tabs.product.adapter.CategoriesAdapter
import com.sun.qakhadelivery.screens.product.ProductFragment
import com.sun.qakhadelivery.screens.signin.OnSignInSuccessListener
import com.sun.qakhadelivery.screens.signin.SignInFragment
import com.sun.qakhadelivery.utils.BasePageFragment
import com.sun.qakhadelivery.utils.UserUtils
import kotlinx.android.synthetic.main.fragment_product_order.*
import org.greenrobot.eventbus.EventBus

class ProductPartnerFragment : BasePageFragment(), ProductPartnerContract.View,
    OnSignInSuccessListener {

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

    override fun onSignInSuccess() {
        EventBus.getDefault().post(Event(EVENT_FRESH_ME, EVENT_FRESH_ME))
    }

    override fun fetchData() {
        initData()
    }

    private fun initViews() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        categoryRecyclerView.apply {
            adapter = categoriesAdapter
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
                    SignInFragment.newInstance().apply {
                        registerSignInSuccessListener(this@ProductPartnerFragment)
                    },
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
        const val EVENT_FRESH_ME = "EVENT_FRESH_ME"

        fun newInstance(partner: Partner?) = ProductPartnerFragment().apply {
            arguments = Bundle().apply {
                putParcelable(BUNDLE_PARTNER, partner)
            }
        }
    }

}
