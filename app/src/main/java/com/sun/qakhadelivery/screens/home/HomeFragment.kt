package com.sun.qakhadelivery.screens.home

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.model.TypePartner
import com.sun.qakhadelivery.screens.home.adapter.SliderAdapter
import com.sun.qakhadelivery.screens.home.adapter.TypePartnerAdapter
import com.sun.qakhadelivery.screens.home.adapter.TypePartnerRecyclerViewOnClickListener
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.delay
import java.lang.Thread.sleep
import java.util.*

class HomeFragment : Fragment(), HomeContract.View, TypePartnerRecyclerViewOnClickListener {

    private val sliderAdapter: SliderAdapter by lazy {
        SliderAdapter()
    }
    private val dataSlider = mutableListOf<Drawable>()
    private val typePartnerAdapter: TypePartnerAdapter by lazy {
        TypePartnerAdapter()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initDataSlider()
    }

    override fun onItemClickListener(typePartner: TypePartner) {
    }

    override fun onGetTypesSuccess(types: MutableList<TypePartner>) {
        typePartnerAdapter.updateData(types)
    }

    override fun onError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun initViews() {
        viewPagerAdvertisement.adapter = sliderAdapter
        recyclerViewTypePartner.adapter = typePartnerAdapter.apply {
            registerRecyclerViewListener(this@HomeFragment)
        }
    }

    private fun initDataSlider() {
        dataSlider.apply {
            ContextCompat.getDrawable(requireContext(), R.drawable.background_partner)?.let { add(it) }
            ContextCompat.getDrawable(requireContext(), R.drawable.background_partner)?.let { add(it) }
            ContextCompat.getDrawable(requireContext(), R.drawable.background_partner)?.let { add(it) }
            ContextCompat.getDrawable(requireContext(), R.drawable.background_partner)?.let { add(it) }
        }
        sliderAdapter.updateSlider(dataSlider)
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}
