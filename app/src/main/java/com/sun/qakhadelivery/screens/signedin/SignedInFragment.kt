package com.sun.qakhadelivery.screens.signedin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.model.User
import com.sun.qakhadelivery.data.repository.UserRepositoryImpl
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefsImpl
import com.sun.qakhadelivery.utils.loadUrl
import kotlinx.android.synthetic.main.fragment_signed_in.*

class SignedInFragment : Fragment(), SignedInContract.View {

    private val presenter by lazy {
        SignedInPresenter(UserRepositoryImpl.getInstance(
                requireContext(),
                SharedPrefsImpl.getInstance(requireContext())
        ))
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
        onGetUserFailureListener?.onGetUserError()
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
    }

    companion object {
        fun newInstance() = SignedInFragment()
    }
}
