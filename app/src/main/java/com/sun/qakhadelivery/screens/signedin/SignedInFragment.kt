package com.sun.qakhadelivery.screens.signedin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.model.Refresh
import com.sun.qakhadelivery.data.model.User
import com.sun.qakhadelivery.data.repository.TokenRepositoryImpl
import com.sun.qakhadelivery.data.repository.UserRepositoryImpl
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefsImpl
import com.sun.qakhadelivery.extensions.addFragmentBackStack
import com.sun.qakhadelivery.extensions.loadAvatarUrl
import com.sun.qakhadelivery.extensions.makeText
import com.sun.qakhadelivery.screens.address.AddressFragment
import com.sun.qakhadelivery.screens.navigate.about.AboutFragment
import com.sun.qakhadelivery.screens.navigate.helpcenter.HelpCenterFragment
import com.sun.qakhadelivery.screens.navigate.setting.SettingFragment
import com.sun.qakhadelivery.screens.profile.ProfileFragment
import com.sun.qakhadelivery.screens.profile.ProfileFragment.Companion.DATA_AVATAR
import kotlinx.android.synthetic.main.fragment_signed_in.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class SignedInFragment : Fragment(), SignedInContract.View {

    private val presenter by lazy {
        SignedInPresenter(
            UserRepositoryImpl.getInstance(),
            TokenRepositoryImpl.getInstance(
                SharedPrefsImpl.getInstance(requireContext())
            )
        )
    }
    private var onGetUserFailureListener: OnGetUserFailureListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_signed_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        handleEvents()
    }

    override fun onStart() {
        super.onStart()
        presenter.setView(this)
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onGetUserSuccess(user: User) {
        imageViewAvatar.loadAvatarUrl(user.image.imageUrl)
        textViewName.text = user.name
    }

    override fun onGetUserFailure() {
        makeText(getString(R.string.please_login_again))
        onGetUserFailureListener?.onGetUserError()
    }

    override fun onSignOutSuccess() {
        activity?.finish()
        activity?.startActivity(activity?.intent)
    }

    override fun onError(message: String) {
        makeText(message)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onRefresh(refresh: Refresh) {
        refresh.message(ProfileFragment::class.java, this::class.java) {
            it.data?.getString(DATA_AVATAR)?.let { url ->
                imageViewAvatar.loadAvatarUrl(url)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        onGetUserFailureListener = null
    }

    fun registerOnGetUserFailureListener(onGetUserFailureListener: OnGetUserFailureListener) {
        this.onGetUserFailureListener = onGetUserFailureListener
    }

    private fun initViews() {
        presenter.getUser()
    }

    private fun handleEvents() {
        signOutButton.setOnClickListener {
            presenter.signOut()
        }
        navProfile.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.userInfoItemMenu -> navigateToFragment(ProfileFragment.newInstance())
                R.id.addressItemMenu -> navigateToFragment(AddressFragment.newInstance())
                R.id.aboutItemMenu -> navigateToFragment(AboutFragment.newInstance())
                R.id.settingsItemMenu -> navigateToFragment(SettingFragment.newInstance())
                R.id.helpCenterItemMenu -> navigateToFragment(HelpCenterFragment.newInstance())
            }
            true
        }
    }

    private fun navigateToFragment(fragment: Fragment) {
        addFragmentBackStack(fragment, R.id.containerView)
    }

    companion object {
        fun newInstance() = SignedInFragment()
    }
}
