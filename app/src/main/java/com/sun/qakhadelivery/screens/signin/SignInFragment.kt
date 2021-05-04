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
import com.sun.qakhadelivery.screens.forgotpassword.ForgotPasswordFragment
import com.sun.qakhadelivery.screens.signup.SignUpFragment
import com.sun.qakhadelivery.utils.Constants
import com.sun.qakhadelivery.extensions.addFragmentSlideAnim
import com.sun.qakhadelivery.extensions.hideKeyboard
import com.sun.qakhadelivery.extensions.makeText
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
        message
        if (message == "Email is not exists. Please sign up !!"){
            emailTextInputLayout.error = getString(R.string.content_email_sign_in)
            passwordTextInputLayout.error = Constants.SPACE_STRING
        }
        if (message == "Sign in failed"){
            emailTextInputLayout.error = Constants.SPACE_STRING
            passwordTextInputLayout.error = getString(R.string.content_password_sign_in)
        }
    }

    override fun onError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun onSignInRoleFailure() {
        Toast.makeText(
            context,
            getString(R.string.you_cannot_sign_in_with_this_account),
            Toast.LENGTH_LONG
        ).show()
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
            hideKeyboard()
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
        containerViewSignIn.setOnClickListener {
            hideKeyboard()
        }
        forgotPasswordTextView.setOnClickListener {
            addFragmentSlideAnim(ForgotPasswordFragment.newInstance(), R.id.containerView)
        }
        imageViewLoginGoogle.setOnClickListener {
            makeText(getString(R.string.coming_soon))
        }
        imageViewLoginFacebook.setOnClickListener {
            makeText(getString(R.string.coming_soon))
        }
    }

    companion object {
        fun newInstance() = SignInFragment()
    }
}
