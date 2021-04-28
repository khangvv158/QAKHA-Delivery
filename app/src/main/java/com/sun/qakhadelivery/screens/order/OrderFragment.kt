package com.sun.qakhadelivery.screens.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.screens.order.adapter.OrderPagerAdapter
import com.sun.qakhadelivery.screens.order.tabs.history.HistoryFragment
import com.sun.qakhadelivery.screens.order.tabs.shipping.ShippingFragment
import kotlinx.android.synthetic.main.fragment_order.*

class OrderFragment : Fragment() {

    private val orderPagerAdapter: OrderPagerAdapter by lazy {
        OrderPagerAdapter(childFragmentManager, requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        initTabLayout()
    }

    private fun initViewPager() {
        viewPagerOrder.apply {
            adapter = orderPagerAdapter.apply {
                addFragment(ShippingFragment.newInstance())
                addFragment(HistoryFragment.newInstance())
            }
            offscreenPageLimit = OFF_SCREEN_PAGE_LIMIT
        }
    }

    private fun initTabLayout() {
        tabLayoutOrder.setupWithViewPager(viewPagerOrder)
    }

    companion object {

        private const val OFF_SCREEN_PAGE_LIMIT = 1

        fun newInstance() = OrderFragment()
    }
}
