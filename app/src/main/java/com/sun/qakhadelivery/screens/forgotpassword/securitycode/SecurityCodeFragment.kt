package com.sun.qakhadelivery.screens.forgotpassword.securitycode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.utils.hideKeyboard
import kotlinx.android.synthetic.main.fragment_security_code.*

class SecurityCodeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_security_code, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageViewBack.setOnClickListener {
            hideKeyboard()
            parentFragmentManager.popBackStack()
        }
    }

    companion object {
        fun newInstance() = SecurityCodeFragment()
    }
}
