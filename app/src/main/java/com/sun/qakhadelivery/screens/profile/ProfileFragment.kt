package com.sun.qakhadelivery.screens.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.model.Refresh
import com.sun.qakhadelivery.data.model.User
import com.sun.qakhadelivery.data.repository.TokenRepositoryImpl
import com.sun.qakhadelivery.data.repository.UserRepositoryImpl
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefsImpl
import com.sun.qakhadelivery.extensions.*
import com.sun.qakhadelivery.screens.profile.update.email.UpdateEmailFragment
import com.sun.qakhadelivery.screens.profile.update.name.UpdateNameFragment
import com.sun.qakhadelivery.screens.profile.update.phone.UpdatePhoneFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class ProfileFragment : Fragment(), ProfileContract.View, View.OnClickListener {

    private val presenter by lazy {
        ProfilePresenter(
            TokenRepositoryImpl.getInstance(
                SharedPrefsImpl.getInstance(requireContext())
            ),
            UserRepositoryImpl.getInstance()
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
        presenter.run {
            setView(this@ProfileFragment)
            getUser()
            disableInteraction()
        }
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onSuccessUser(user: User) {
        avatarCircleImageView.loadUrl(user.image.imageUrl)
        nameNavigateView.setDescribe(user.name)
        phoneNumberNavigateView.setDescribe(user.phoneNumber)
        emailNavigateView.setDescribe(user.email)
        arguments = Bundle().apply {
            putParcelable(BUNDLE_USER, user)
        }
        enableInteraction()
    }

    override fun onErrorUser(exception: String) {
        enableInteraction()
        makeText(exception)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onRefresh(refresh: Refresh) {
        refresh.message(this::class.java) {
            presenter.getUser()
            disableInteraction()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.avatarLayout -> {
            }
            R.id.nameNavigateView -> {
                addFragmentSlideAnim(
                    UpdateNameFragment.newInstance(arguments?.getParcelable(BUNDLE_USER)),
                    R.id.containerView
                )
            }
            R.id.phoneNumberNavigateView -> {
                addFragmentSlideAnim(
                    UpdatePhoneFragment.newInstance(arguments?.getParcelable(BUNDLE_USER)),
                    R.id.containerView
                )
            }
            R.id.emailNavigateView -> {
                addFragmentSlideAnim(
                    UpdateEmailFragment.newInstance(arguments?.getParcelable(BUNDLE_USER)),
                    R.id.containerView
                )
            }
        }
    }

    private fun handleEvent() {
        avatarLayout.setOnSafeClickListener(listener = this)
        nameNavigateView.setOnSafeClickListener(listener = this)
        phoneNumberNavigateView.setOnSafeClickListener(listener = this)
        emailNavigateView.setOnSafeClickListener(listener = this)
    }

    private fun disableInteraction() {
        loadingProgress.show()
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    private fun enableInteraction() {
        loadingProgress.gone()
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    companion object {
        private const val BUNDLE_USER = "BUNDLE_USER"

        fun newInstance() = ProfileFragment()
    }
}
