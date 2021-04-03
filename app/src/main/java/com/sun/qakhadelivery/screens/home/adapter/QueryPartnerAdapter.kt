package com.sun.qakhadelivery.screens.home.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.screens.home.tabs.all.AllFragment
import com.sun.qakhadelivery.screens.home.tabs.bestrated.BestRatedFragment
import com.sun.qakhadelivery.screens.home.tabs.nearby.NearbyFragment
import com.sun.qakhadelivery.screens.home.tabs.topsales.TopSaleFragment
import com.sun.qakhadelivery.utils.Constants

class QueryPartnerAdapter(
    manager: FragmentManager,
    private val context: Context,
    private val fragments: MutableList<Fragment> = arrayListOf()
) : FragmentPagerAdapter(manager) {

    override fun getCount() = fragments.size

    override fun getItem(position: Int) = fragments[position]

    override fun getPageTitle(position: Int): CharSequence? {
        return when (fragments[position]::class.java.simpleName) {
            AllFragment::class.java.simpleName -> context.getString(R.string.all)
            NearbyFragment::class.java.simpleName -> context.getString(R.string.nearby)
            TopSaleFragment::class.java.simpleName -> context.getString(R.string.top_sales)
            BestRatedFragment::class.java.simpleName -> context.getString(R.string.best_rated)
            else -> Constants.SPACE_STRING
        }
    }

    fun addFragment(fragment: Fragment) {
        fragments.add(fragment)
    }
}
