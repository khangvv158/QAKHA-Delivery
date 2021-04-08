package com.sun.qakhadelivery.screens.home

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.model.TypePartner
import com.sun.qakhadelivery.data.repository.PartnerRepositoryImpl
import com.sun.qakhadelivery.screens.home.adapter.QueryPartnerPageAdapter
import com.sun.qakhadelivery.screens.home.adapter.SliderAdapter
import com.sun.qakhadelivery.screens.home.adapter.TypePartnerAdapter
import com.sun.qakhadelivery.screens.home.adapter.TypePartnerRecyclerViewOnClickListener
import com.sun.qakhadelivery.screens.home.tabs.all.AllFragment
import com.sun.qakhadelivery.screens.home.tabs.bestrated.BestRatedFragment
import com.sun.qakhadelivery.screens.home.tabs.nearby.NearbyFragment
import com.sun.qakhadelivery.screens.home.tabs.topsales.TopSaleFragment
import com.sun.qakhadelivery.widget.recyclerview.item.TypePartnerItem
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.indicatorView
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.greenrobot.eventbus.EventBus

class HomeFragment : Fragment(), HomeContract.View, TypePartnerRecyclerViewOnClickListener {

    private val dataSlider = mutableListOf<Drawable>()
    private val typePartnerAdapter: TypePartnerAdapter by lazy {
        TypePartnerAdapter()
    }
    private lateinit var sliderAdapter: SliderAdapter
    private val queryPartnerAdapter: QueryPartnerPageAdapter by lazy {
        QueryPartnerPageAdapter(childFragmentManager, requireContext())
    }
    private val presenter by lazy {
        HomePresenter(PartnerRepositoryImpl.getInstance())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSliderView()
        initTypeView()
        initTypeData()
        initPagerView()
        initTabLayout()
    }

    override fun onResume() {
        super.onResume()
        viewPagerSlider.resumeAutoScroll()
    }

    override fun onPause() {
        super.onPause()
        viewPagerSlider.pauseAutoScroll()
    }

    override fun onItemClickListener(typePartner: TypePartner) {
        EventBus.getDefault().post(typePartner)
    }

    override fun onGetTypesSuccess(types: MutableList<TypePartner>) {
        typePartnerAdapter.updateData(types)
    }

    override fun onError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun initSliderView() {
        presenter.setView(this@HomeFragment)
        initSliderData()
        sliderAdapter = SliderAdapter(requireContext(), dataSlider, true)
        with(viewPagerSlider) {
            adapter = sliderAdapter
        }
        indicatorView.highlighterViewDelegate = {
            val highlighter = View(requireContext())
            highlighter.layoutParams = FrameLayout.LayoutParams(16, 2)
            highlighter.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.colorWhite))
            highlighter
        }
        indicatorView.unselectedViewDelegate = {
            val unselected = View(requireContext())
            unselected.layoutParams = LinearLayout.LayoutParams(16, 2)
            unselected.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.colorWhite))
            unselected.alpha = 0.4f
            unselected
        }
        indicatorView.updateIndicatorCounts(viewPagerSlider.indicatorCount)
        viewPagerSlider.onIndicatorProgress = { selectingPosition, progress ->
            indicatorView.onPageScrolled(selectingPosition, progress)
        }
    }

    private fun initSliderData() {
        dataSlider.apply {
            ContextCompat.getDrawable(requireContext(), R.drawable.banner_food_1)
                ?.let { add(it) }
            ContextCompat.getDrawable(requireContext(), R.drawable.banner_food_2)
                ?.let { add(it) }
            ContextCompat.getDrawable(requireContext(), R.drawable.banner_food_3)
                ?.let { add(it) }
            ContextCompat.getDrawable(requireContext(), R.drawable.background_partner)
                ?.let { add(it) }
        }
    }

    private fun initTypeView() {
        recyclerViewTypePartner.adapter = typePartnerAdapter.apply {
            registerRecyclerViewListener(this@HomeFragment)
        }
    }

    private fun initTypeData() {
        typePartnerAdapter.addItem(TypePartnerItem(TypePartner(-1, "All")))
        presenter.getTypes()
    }

    private fun initPagerView() {
        viewPagerPartner.apply {
            offscreenPageLimit = OFF_SCREEN_PAGE_LIMIT
            adapter = queryPartnerAdapter.apply {
                addFragment(AllFragment.newInstance())
                addFragment(NearbyFragment.newInstance())
                addFragment(TopSaleFragment.newInstance())
                addFragment(BestRatedFragment.newInstance())
            }
        }
    }

    private fun initTabLayout() {
        tabLayoutTypePartner.setupWithViewPager(viewPagerPartner)
    }

    companion object {
        const val BUNDLE_PARTNER = "BUNDLE_PARTNER"
        private const val OFF_SCREEN_PAGE_LIMIT = 4

        fun newInstance() = HomeFragment()
    }
}
