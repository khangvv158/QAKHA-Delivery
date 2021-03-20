package com.sun.qakhadelivery.screens.me

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.screens.notsignin.NotSignInFragment
import com.sun.qakhadelivery.utils.addFragment

class MeFragment : Fragment(), MeContract.View {

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

    private fun initViews() {
        addFragment(NotSignInFragment.newInstance(), R.id.meContainerView)
    }

    companion object {
        fun newInstance() = MeFragment()
    }
}
