package com.sun.qakhadelivery.screens.notsignin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.screens.signin.OnSignInSuccessListener
import com.sun.qakhadelivery.screens.signin.SignInFragment
import com.sun.qakhadelivery.utils.addFragmentSlideAnim
import kotlinx.android.synthetic.main.fragment_not_sign_in.*

class NotSignInFragment : Fragment(), OnSignInSuccessListener {

    private var onSignInSuccessListener: OnSignInSuccessListener? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_not_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleEvents()
    }

    override fun onSignInSuccess() {
        onSignInSuccessListener?.onSignInSuccess()
    }

    fun registerSignInSuccessListener(onSignInSuccessListener: OnSignInSuccessListener) {
        this.onSignInSuccessListener = onSignInSuccessListener
    }

    private fun handleEvents() {
        authenticationButton.setOnClickListener {
            addFragmentSlideAnim(SignInFragment.newInstance().apply {
                registerSignInSuccessListener(this@NotSignInFragment)
            }, R.id.containerView)
        }
    }

    companion object {
        fun newInstance() = NotSignInFragment()
    }
}
