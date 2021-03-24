package com.sun.qakhadelivery.screens.partner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.bumptech.glide.Glide
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.model.Product
import com.sun.qakhadelivery.screens.partner.adaper.PartnerPagerAdapter
import com.sun.qakhadelivery.screens.partner.cart.CartFragment
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
    private val cartBottomSheet by lazy { CartFragment() }

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
        setFragmentResultListener(ProductPartnerFragment.KEY_REQUEST) { _, bundle ->
            bundle.getParcelable<Product>(ProductPartnerFragment.EXTRA_PRODUCT)?.let { product ->
                cartBottomSheet.apply {
                    addProduct(product)
                }
            }
        }
        cartFloatButton.setOnClickListener {
            if (!cartBottomSheet.isAdded) {
                cartBottomSheet.show(childFragmentManager, cartBottomSheet::class.java.simpleName)
            }
        }
        cartBottomSheet.setOnCountListener {
            updateCounter(it)
        }
    }

    private fun updateCounter(counter: Int) {
        val shake: Animation = AnimationUtils.loadAnimation(context, R.anim.animation_shake)
        counterCartTextView.apply {
            text = counter.toString()
            startAnimation(shake)
        }
    }

    companion object {
        private const val OFF_SCREEN_PAGE_LIMIT = 3

        fun newInstance() = PartnerFragment()
    }
}
