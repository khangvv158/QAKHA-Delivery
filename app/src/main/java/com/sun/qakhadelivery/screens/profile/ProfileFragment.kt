package com.sun.qakhadelivery.screens.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.repository.TokenRepositoryImpl
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefsImpl
import com.sun.qakhadelivery.extensions.addFragmentSlideAnim
import com.sun.qakhadelivery.extensions.setOnSafeClickListener
import com.sun.qakhadelivery.screens.profile.update.email.UpdateEmailFragment
import com.sun.qakhadelivery.screens.profile.update.name.UpdateNameFragment
import com.sun.qakhadelivery.screens.profile.update.phone.UpdatePhoneFragment
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment(), ProfileContract.View, View.OnClickListener {

    private val presenter by lazy {
        ProfilePresenter(
            TokenRepositoryImpl.getInstance(
                SharedPrefsImpl.getInstance(requireContext())
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleEvent()
    }

    override fun onStart() {
        super.onStart()
        presenter.setView(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.avatarLayout -> {
            }
            R.id.nameNavigateView -> {
                addFragmentSlideAnim(UpdateNameFragment.newInstance(), R.id.containerView)
            }
            R.id.phoneNumberNavigateView -> {
                addFragmentSlideAnim(UpdatePhoneFragment.newInstance(), R.id.containerView)
            }
            R.id.emailNavigateView -> {
                addFragmentSlideAnim(UpdateEmailFragment.newInstance(), R.id.containerView)
            }
        }
    }

    private fun handleEvent() {
        avatarLayout.setOnSafeClickListener(listener = this)
        nameNavigateView.setOnSafeClickListener(listener = this)
        phoneNumberNavigateView.setOnSafeClickListener(listener = this)
        emailNavigateView.setOnSafeClickListener(listener = this)
    }

    companion object {

        fun newInstance() = ProfileFragment()
    }
}
