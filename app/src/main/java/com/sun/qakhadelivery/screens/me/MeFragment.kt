package com.sun.qakhadelivery.screens.me

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.model.Event
import com.sun.qakhadelivery.data.model.Refresh
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefsImpl
import com.sun.qakhadelivery.extensions.loadAvatarUrl
import com.sun.qakhadelivery.extensions.replaceFragment
import com.sun.qakhadelivery.screens.notsignin.NotSignInFragment
import com.sun.qakhadelivery.screens.partner.tabs.product.ProductPartnerFragment
import com.sun.qakhadelivery.screens.profile.ProfileFragment
import com.sun.qakhadelivery.screens.signedin.OnGetUserFailureListener
import com.sun.qakhadelivery.screens.signedin.SignedInFragment
import com.sun.qakhadelivery.screens.signin.OnSignInSuccessListener
import com.sun.qakhadelivery.screens.signin.SignInPresenter
import kotlinx.android.synthetic.main.fragment_signed_in.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MeFragment : Fragment(), MeContract.View,
    OnSignInSuccessListener, OnGetUserFailureListener {

    private val presenter by lazy {
        MePresenter(SharedPrefsImpl.getInstance(requireContext()))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        EventBus.getDefault().register(this)
    }

    override fun onDetach() {
        EventBus.getDefault().unregister(this)
        super.onDetach()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_me, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.setView(this)
        initViews()
    }

    override fun onStop() {
        presenter.onStop()
        super.onStop()
    }

    override fun onSignInSuccess() {
        navigateSignedInFragment()
    }

    override fun onCheckSignedInSuccess() {
        navigateSignedInFragment()
    }

    override fun onCheckSignedInFailure() {
        navigateNotSignedInFragment()
    }

    override fun onGetUserError() {
        navigateNotSignedInFragment()
        activity?.finish()
        activity?.startActivity(activity?.intent)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onRefresh(event: Event<String>) {
        if (event.keyEvent == ProductPartnerFragment.EVENT_FRESH_ME) {
            navigateSignedInFragment()
        }
    }

    private fun navigateNotSignedInFragment() {
        replaceFragment(NotSignInFragment.newInstance().apply {
            registerSignInSuccessListener(this@MeFragment)
        }, R.id.meContainerView)
    }

    private fun navigateSignedInFragment() {
        replaceFragment(SignedInFragment.newInstance().apply {
            registerOnGetUserFailureListener(this@MeFragment)
        }, R.id.meContainerView)
    }

    private fun initViews() {
        presenter.checkSignedIn()
    }

    companion object {
        fun newInstance() = MeFragment()
    }
}
