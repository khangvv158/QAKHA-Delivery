package com.sun.qakhadelivery.screens.partner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.screens.partner.adaper.PartnerPagerAdapter
import com.sun.qakhadelivery.screens.partner.tabs.information.InfoPartnerFragment
import com.sun.qakhadelivery.screens.partner.tabs.product.ProductPartnerFragment
import com.sun.qakhadelivery.screens.partner.tabs.review.ReviewFragment
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.fragment_partner.*

class PartnerFragment : Fragment(), PartnerContract.View {

    private val pagerAdapter by lazy {
        PartnerPagerAdapter(
            parentFragmentManager,
            requireContext()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_partner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        handleEvent()
    }

    private fun initViews() {
        initData()
        initViewPager()
        initTabLayout()
    }

    private fun initData() {
        Glide.with(this)
            .load(R.drawable.background_partner)
            .transform(BlurTransformation(3, 2))
            .into(partnerImageView)
    }

    private fun initTabLayout() {
        partnerTabLayout.setupWithViewPager(partnerViewPager)
    }

    private fun initViewPager() {
        partnerViewPager.apply {
            adapter = pagerAdapter.apply {
                addFragment(ProductPartnerFragment.newInstance())
                addFragment(ReviewFragment.newInstance())
                addFragment(InfoPartnerFragment.newInstance())
            }
            offscreenPageLimit = OFF_SCREEN_PAGE_LIMIT
        }
    }

    private fun handleEvent() {
        backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    companion object {
        private const val OFF_SCREEN_PAGE_LIMIT = 3

        fun newInstance() = PartnerFragment()
    }
}
