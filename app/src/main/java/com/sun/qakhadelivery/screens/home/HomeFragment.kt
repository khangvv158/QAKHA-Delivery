package com.sun.qakhadelivery.screens.home

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.model.TypePartner
import com.sun.qakhadelivery.data.repository.PartnerRepositoryImpl
import com.sun.qakhadelivery.screens.home.adapter.QueryPartnerAdapter
import com.sun.qakhadelivery.screens.home.adapter.SliderAdapter
import com.sun.qakhadelivery.screens.home.adapter.TypePartnerAdapter
import com.sun.qakhadelivery.screens.home.adapter.TypePartnerRecyclerViewOnClickListener
import com.sun.qakhadelivery.screens.home.tabs.all.AllFragment
import com.sun.qakhadelivery.screens.home.tabs.bestrated.BestRatedFragment
import com.sun.qakhadelivery.screens.home.tabs.nearby.NearbyFragment
import com.sun.qakhadelivery.screens.home.tabs.topsales.TopSaleFragment
import com.sun.qakhadelivery.widget.recyclerview.item.TypePartnerItem
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

class HomeFragment : Fragment(), HomeContract.View, TypePartnerRecyclerViewOnClickListener {

    private val dataSlider = mutableListOf<Drawable>()
    private val typePartnerAdapter: TypePartnerAdapter by lazy {
        TypePartnerAdapter()
    }
    private val sliderAdapter: SliderAdapter by lazy {
        SliderAdapter()
    }
    private val queryPartnerAdapter: QueryPartnerAdapter by lazy {
        QueryPartnerAdapter(childFragmentManager, requireContext())
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
        initSliderData()
        initTypeView()
        initTypeData()
        initPagerView()
        initTabLayout()
    }

    override fun onItemClickListener(typePartner: TypePartner) {
    }

    override fun onGetTypesSuccess(types: MutableList<TypePartner>) {
        typePartnerAdapter.updateData(types)
    }

    override fun onError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun initSliderView() {
        presenter.setView(this@HomeFragment)
        viewPagerAdvertisement.adapter = sliderAdapter

    }

    private fun initPagerView() {
        viewPagerPartner.apply {
            adapter = queryPartnerAdapter.apply {
                addFragment(AllFragment.newInstance())
                addFragment(NearbyFragment.newInstance())
                addFragment(TopSaleFragment.newInstance())
                addFragment(BestRatedFragment.newInstance())
            }
            offscreenPageLimit = OFF_SCREEN_PAGE_LIMIT
        }
    }

    private fun initTypeView() {
        recyclerViewTypePartner.adapter = typePartnerAdapter.apply {
            registerRecyclerViewListener(this@HomeFragment)
        }
    }

    private fun initTabLayout() {
        tabLayoutTypePartner.setupWithViewPager(viewPagerPartner)
    }

    private fun initTypeData() {
        presenter.getTypes()
        typePartnerAdapter.addItem(TypePartnerItem(TypePartner(0, "Food")))
        typePartnerAdapter.addItem(TypePartnerItem(TypePartner(1, "Street food")))
        typePartnerAdapter.addItem(TypePartnerItem(TypePartner(2, "Cake")))
        typePartnerAdapter.addItem(TypePartnerItem(TypePartner(3, "Noodle")))
        typePartnerAdapter.addItem(TypePartnerItem(TypePartner(4, "Pho")))
    }

    private fun initSliderData() {
        dataSlider.apply {
            ContextCompat.getDrawable(requireContext(), R.drawable.background_partner)
                    ?.let { add(it) }
            ContextCompat.getDrawable(requireContext(), R.drawable.background_partner)
                    ?.let { add(it) }
            ContextCompat.getDrawable(requireContext(), R.drawable.background_partner)
                    ?.let { add(it) }
            ContextCompat.getDrawable(requireContext(), R.drawable.background_partner)
                    ?.let { add(it) }
        }
        sliderAdapter.updateSlider(dataSlider)
    }

    companion object {

        private const val OFF_SCREEN_PAGE_LIMIT = 1

        fun newInstance() = HomeFragment()
    }
}
