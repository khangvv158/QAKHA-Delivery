package com.sun.qakhadelivery.screens.home.tabs.bestrated

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.qakhadelivery.R

class BestRatedFragment : Fragment() {

    companion object {
        fun newInstance() = BestRatedFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_best_rated, container, false)
    }
}
