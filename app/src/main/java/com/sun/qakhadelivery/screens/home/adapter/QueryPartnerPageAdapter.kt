package com.sun.qakhadelivery.screens.home.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.screens.home.tabs.all.AllFragment
import com.sun.qakhadelivery.screens.home.tabs.bestrated.BestRatedFragment
import com.sun.qakhadelivery.screens.home.tabs.nearby.NearbyFragment
import com.sun.qakhadelivery.utils.Constants

class QueryPartnerPageAdapter(
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
            AllFragment::class.java.simpleName -> context.getString(R.string.all)
            NearbyFragment::class.java.simpleName -> context.getString(R.string.nearby)
            BestRatedFragment::class.java.simpleName -> context.getString(R.string.best_rated)
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
