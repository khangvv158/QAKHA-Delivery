package com.sun.qakhadelivery.screens.orderdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.screens.orderdetail.adapter.BucketAdapter
import kotlinx.android.synthetic.main.fragment_order_detail.*

class OrderDetailFragment : Fragment() {

    private val adapter: BucketAdapter by lazy {
        BucketAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_order_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        recyclerViewBucket.adapter = adapter
    }

    private fun initData() {
    }

    companion object {
        fun newInstance() = OrderDetailFragment()
    }
}
