package com.sun.qakhadelivery.screens.home.tabs.topsales

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.qakhadelivery.R

class TopSaleFragment : Fragment() {

    companion object {
        fun newInstance() = TopSaleFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_top_sale, container, false)
    }
}
