package com.sun.qakhadelivery.screens.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.screens.orderdetail.adapter.BucketAdapter
import com.sun.qakhadelivery.utils.getBucket
import kotlinx.android.synthetic.main.fragment_checkout.*

class CheckoutFragment : Fragment(), CheckoutContract.View {

    private val adapter: BucketAdapter by lazy {
        BucketAdapter()
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

    private fun initViews() {
        recyclerViewBucket.adapter = adapter
    }

    private fun initData() {
        adapter.updateData(getBucket())
        textViewPriceSubtotal.text = adapter.geSubtotalPrice().toString()
    }

    companion object {

        fun newInstance() = CheckoutFragment()
    }
}
