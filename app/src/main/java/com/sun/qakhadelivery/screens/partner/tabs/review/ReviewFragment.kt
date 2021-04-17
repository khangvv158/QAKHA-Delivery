package com.sun.qakhadelivery.screens.partner.tabs.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sun.qakhadelivery.R

class ReviewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_review, container, false)
    }

    companion object {

        fun newInstance(bundle: Bundle?) = ReviewFragment().apply {
            arguments = bundle
        }
    }
}
