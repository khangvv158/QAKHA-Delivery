package com.sun.qakhadelivery.screens.home

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.screens.home.adapter.SliderAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), HomeContract.View {

    private val adapter: SliderAdapter by lazy {
        SliderAdapter()
    }
    private val dataSlider = mutableListOf<Drawable>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initData()
    }

    private fun initViews() {
        viewPagerAdvertisement.adapter = adapter
    }

    private fun initData() {
        dataSlider.apply {
            ContextCompat.getDrawable(requireContext(), R.drawable.background_partner)?.let { add(it) }
            ContextCompat.getDrawable(requireContext(), R.drawable.background_partner)?.let { add(it) }
            ContextCompat.getDrawable(requireContext(), R.drawable.background_partner)?.let { add(it) }
            ContextCompat.getDrawable(requireContext(), R.drawable.background_partner)?.let { add(it) }
        }
        adapter.updateSlider(dataSlider)
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}
