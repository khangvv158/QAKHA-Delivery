package com.sun.qakhadelivery.screens.container

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.screens.container.adapter.ContainerPagerAdapter
import com.sun.qakhadelivery.screens.home.HomeFragment
import com.sun.qakhadelivery.screens.me.MeFragment
import com.sun.qakhadelivery.screens.order.OrderFragment
import com.sun.qakhadelivery.utils.TypeMenu
import kotlinx.android.synthetic.main.fragment_container.*

class ContainerFragment : Fragment() {

    private val adapter: ContainerPagerAdapter by lazy {
        ContainerPagerAdapter(parentFragmentManager)
    }
    private val homeFragment = HomeFragment.newInstance()
    private val orderFragment = OrderFragment.newInstance()
    private val meFragment = MeFragment.newInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerEvents()
        initData()
        initViews()
        handleEvents()
    }

    private fun registerEvents() = Unit

    private fun initData() {
        adapter.apply {
            addFragment(homeFragment)
            addFragment(orderFragment)
            addFragment(meFragment)
        }
    }

    private fun initViews() {
        containerViewPager.apply {
            adapter = this@ContainerFragment.adapter
            offscreenPageLimit = OFF_SCREEN_PAGE_LIMIT
        }
    }

    private fun handleEvents() {
        containerViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) = Unit

            override fun onPageSelected(position: Int) {
                bottomNavigationView.selectedItemId =
                    bottomNavigationView.menu.getItem(position).itemId
            }

            override fun onPageScrollStateChanged(state: Int) = Unit
        })
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item_menu_nav_home -> {
                    containerViewPager.currentItem = TypeMenu.HOME.ordinal
                    true
                }
                R.id.item_menu_nav_order -> {
                    containerViewPager.currentItem = TypeMenu.ORDER.ordinal
                    true
                }
                R.id.item_menu_nav_me -> {
                    containerViewPager.currentItem = TypeMenu.ME.ordinal
                    true
                }
                else -> false
            }
        }
    }

    companion object {

        const val OFF_SCREEN_PAGE_LIMIT = 4

        fun newInstance() = ContainerFragment()
    }
}
