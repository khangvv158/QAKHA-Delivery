package com.sun.qakhadelivery.screens.partner.tabs.information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sun.qakhadelivery.R

class InfoPartnerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_info_partner, container, false)
    }

    companion object {

        fun newInstance() = InfoPartnerFragment()
    }
}
