package com.sun.qakhadelivery.screens.order.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.screens.order.tabs.history.HistoryFragment
import com.sun.qakhadelivery.screens.order.tabs.shipping.ShippingFragment
import com.sun.qakhadelivery.utils.Constants

class OrderPagerAdapter(
        manager: FragmentManager,
        private val context: Context,
        private val fragments: MutableList<Fragment> = arrayListOf()
) : FragmentPagerAdapter(
        manager,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {

    override fun getCount() = fragments.size

    override fun getItem(position: Int) = fragments[position]

    override fun getPageTitle(position: Int): CharSequence? {
        return when (fragments[position]::class.java.simpleName) {
            ShippingFragment::class.java.simpleName -> context.getString(R.string.shipping)
            HistoryFragment::class.java.simpleName -> context.getString(R.string.history)
            else -> Constants.SPACE_STRING
        }
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    fun addFragment(fragment: Fragment) {
        fragments.add(fragment)
    }
}
