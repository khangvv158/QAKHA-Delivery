package com.sun.qakhadelivery.screens.notsignin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.qakhadelivery.R

class NotSignInFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_not_sign_in, container, false)
    }

    companion object {
        fun newInstance() = NotSignInFragment()
    }
}
