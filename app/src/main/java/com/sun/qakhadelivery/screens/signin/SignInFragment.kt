package com.sun.qakhadelivery.screens.signin

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.repository.SignRepositoryImpl
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefsImpl
import com.sun.qakhadelivery.screens.signup.SignUpFragment
import com.sun.qakhadelivery.utils.Constants
import com.sun.qakhadelivery.utils.addFragmentSlideAnim
import kotlinx.android.synthetic.main.fragment_sign_in.*

class SignInFragment : Fragment(), SignInContract.View {

    private val presenter by lazy {
        SignInPresenter(
                SignRepositoryImpl.getInstance(
                        SharedPrefsImpl.getInstance(requireContext())
                )
        )
    }
    private var onSignInSuccessListener: OnSignInSuccessListener? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.setView(this)
        handleEvents()
    }

    override fun onSignInSuccess() {
        onSignInSuccessListener?.onSignInSuccess()
        parentFragmentManager.popBackStack()
    }

    override fun onSignInFailure(message: String) {
        emailTextInputLayout.error = Constants.SPACE_STRING
        passwordTextInputLayout.error = message
    }

    override fun onError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun registerSignInSuccessListener(onSignInSuccessListener: OnSignInSuccessListener) {
        this.onSignInSuccessListener = onSignInSuccessListener
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun handleEvents() {
        imageViewBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        textViewSignUp.setOnClickListener {
            addFragmentSlideAnim(SignUpFragment.newInstance(), R.id.containerView)
        }
        signInButton.setOnClickListener {
            presenter.signIn(emailEditText.text.toString(), passwordEditText.text.toString())
        }

        passwordEditText.setOnTouchListener { _, _ ->
            passwordTextInputLayout.error = null
            passwordTextInputLayout.isErrorEnabled = false
            false
        }
        emailEditText.setOnTouchListener { _, _ ->
            emailTextInputLayout.error = null
            emailTextInputLayout.isErrorEnabled = false
            false
        }
    }

    companion object {
        fun newInstance() = SignInFragment()
    }
}
