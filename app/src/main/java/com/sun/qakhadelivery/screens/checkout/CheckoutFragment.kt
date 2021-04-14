package com.sun.qakhadelivery.screens.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.model.Cart
import com.sun.qakhadelivery.data.model.Partner
import com.sun.qakhadelivery.data.repository.CartRepositoryImpl
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefsImpl
import com.sun.qakhadelivery.screens.orderdetail.adapter.BucketAdapter
import kotlinx.android.synthetic.main.fragment_checkout.*

class CheckoutFragment : Fragment(), CheckoutContract.View {

    private val adapter: BucketAdapter by lazy {
        BucketAdapter()
    }
    private val presenter: CheckoutPresenter by lazy {
        CheckoutPresenter(
            CartRepositoryImpl.getInstance(
                SharedPrefsImpl.getInstance(requireContext())
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_checkout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initData()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun onSuccessGetCart(carts: MutableList<Cart>) {
        adapter.updateData(carts)
    }

    override fun onErrorGetCart(exception: String) = Unit

    override fun onUpdateTotalPrice(total: Float) {
        textViewPriceSubtotal.text = total.toString()
    }

    private fun initViews() {
        recyclerViewBucket.adapter = adapter
    }

    private fun initData() {
        arguments?.getParcelable<Partner>(BUNDLE_PARTNER)?.let {
            val products = it.categories
                .flatMap { category -> category.products }
                .toMutableList()
            presenter.run {
                setView(this@CheckoutFragment)
                getCart(it.id, products)
            }
        }
    }

    companion object {
        const val BUNDLE_PARTNER = "BUNDLE_PARTNER"

        fun newInstance(bundle: Bundle?) = CheckoutFragment().apply {
            arguments = bundle
        }
    }
}
