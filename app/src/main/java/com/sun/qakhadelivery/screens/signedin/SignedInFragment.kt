package com.sun.qakhadelivery.screens.signedin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.model.User
import com.sun.qakhadelivery.data.repository.UserRepositoryImpl
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefsImpl
import com.sun.qakhadelivery.extensions.addFragmentBackStack
import com.sun.qakhadelivery.extensions.loadUrl
import com.sun.qakhadelivery.screens.address.AddressFragment
import kotlinx.android.synthetic.main.fragment_signed_in.*

class SignedInFragment : Fragment(), SignedInContract.View {

    private val presenter by lazy {
        SignedInPresenter(
            UserRepositoryImpl.getInstance(
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
        presenter.setView(this)
        initViews()
        handleEvents()
    }

    override fun onGetUserSuccess(user: User) {
        user.image?.let { imageViewAvatar.loadUrl(it) }
        textViewName.text = user.name
    }

    override fun onGetUserFailure() {
        onGetUserFailureListener?.onGetUserError()
    }

    override fun onSignOutSuccess() {
        activity?.finish()
        activity?.startActivity(activity?.intent)
    }

    override fun onError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
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
                R.id.payItemMenu -> {
                }
                R.id.addressItemMenu -> navigateAddress()
                R.id.inviteFriendsItemMenu -> {
                }
                R.id.helpCenterItemMenu -> {
                }
            }
            true
        }
    }

    private fun navigateAddress() {
        addFragmentBackStack(AddressFragment.newInstance(), R.id.containerView)
    }

    companion object {
        fun newInstance() = SignedInFragment()
    }
}
