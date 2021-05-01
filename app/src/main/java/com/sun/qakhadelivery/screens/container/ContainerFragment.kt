package com.sun.qakhadelivery.screens.container

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEachIndexed
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.model.Refresh
import com.sun.qakhadelivery.screens.container.adapter.ContainerPagerAdapter
import com.sun.qakhadelivery.screens.home.HomeFragment
import com.sun.qakhadelivery.screens.me.MeFragment
import com.sun.qakhadelivery.screens.order.OrderFragment
import com.sun.qakhadelivery.utils.TypeMenu
import kotlinx.android.synthetic.main.fragment_container.*
import kotlinx.android.synthetic.main.fragment_shipping.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

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
        initData()
        initViews()
        handleEvents()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onLoadRefresh(refresh: Refresh) {
        refresh.message {
            bottomNavigationView.menu.forEachIndexed { index, item ->
                if (item.itemId == R.id.item_menu_nav_order) {
                    containerViewPager.currentItem = index
                }
            }
        }
    }

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
