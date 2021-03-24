package com.sun.qakhadelivery.screens.me

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.screens.notsignin.NotSignInFragment
import com.sun.qakhadelivery.screens.signedin.SignedInFragment
import com.sun.qakhadelivery.screens.signin.OnSignInSuccessListener
import com.sun.qakhadelivery.utils.addFragment
import com.sun.qakhadelivery.utils.replaceFragment

class MeFragment : Fragment(), MeContract.View, OnSignInSuccessListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_me, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onSignInSuccess() {
        replaceFragment(SignedInFragment.newInstance(), R.id.meContainerView)
    }

    private fun initViews() {
        addFragment(NotSignInFragment.newInstance().apply {
            registerSignInSuccessListener(this@MeFragment)
        }, R.id.meContainerView)
    }

    companion object {
        fun newInstance() = MeFragment()
    }
}
