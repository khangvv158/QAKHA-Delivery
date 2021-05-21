package com.sun.qakhadelivery.screens.partner.adaper

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.screens.partner.tabs.information.InfoPartnerFragment
import com.sun.qakhadelivery.screens.partner.tabs.product.ProductPartnerFragment
import com.sun.qakhadelivery.screens.partner.tabs.review.ReviewFragment

class PartnerPagerAdapter(
    manager: FragmentManager,
    private val context: Context,
    private val fragments: MutableList<Fragment> = arrayListOf()
) : FragmentPagerAdapter(
    manager,
    BEHAVIOR_SET_USER_VISIBLE_HINT
) {
    override fun getCount() = fragments.size

    override fun getItem(position: Int) = fragments[position]

    override fun getPageTitle(position: Int): CharSequence? {
        return when (fragments[position]::class.java.simpleName) {
            ProductPartnerFragment::class.java.simpleName -> context.getString(R.string.tab_product_order)
            ReviewFragment::class.java.simpleName -> context.getString(R.string.tab_review)
            InfoPartnerFragment::class.java.simpleName -> context.getString(R.string.tab_information)
            else -> context.getString(R.string.tab_product_order)
        }
    }

    fun addFragment(fragment: Fragment) {
        fragments.add(fragment)
    }
}
