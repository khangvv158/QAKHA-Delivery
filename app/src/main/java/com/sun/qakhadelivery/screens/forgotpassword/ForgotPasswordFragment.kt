package com.sun.qakhadelivery.screens.forgotpassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.repository.SignRepositoryImpl
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefsImpl
import com.sun.qakhadelivery.extensions.*
import com.sun.qakhadelivery.screens.forgotpassword.resetpassword.ResetPasswordFragment
import com.sun.qakhadelivery.screens.forgotpassword.resetpassword.ResetPasswordSuccess
import com.sun.qakhadelivery.utils.*
import kotlinx.android.synthetic.main.fragment_forgot_password.*

class ForgotPasswordFragment : Fragment(), ForgotPasswordContract.View, ResetPasswordSuccess {

    private val presenter by lazy {
        ForgotPasswordPresenter(
            SignRepositoryImpl.getInstance(
                SharedPrefsImpl.getInstance(requireContext())
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_forgot_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.setView(this)
        handleEvents()
    }

    override fun onResetPasswordSuccess() {
        parentFragmentManager.popBackStack()
    }

    override fun onForgotPasswordSuccess(email: String) {
        progressBar.gone()
        addFragmentFadeAnim(ResetPasswordFragment.newInstance(email).apply {
            registerListener(this@ForgotPasswordFragment)
        }, R.id.containerView)
    }

    override fun onForgotPasswordFailure() {
        progressBar.gone()
        emailEditTextLayout.error =
            getString(R.string.email_address_not_found_please_check_and_try_again)
    }

    override fun onError(message: String) {
        progressBar.gone()
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun validateEmail(email: String): Boolean {
        return if (email.isEmpty()) {
            emailEditTextLayout.error = getString(R.string.email_cant_be_blank)
            false
        } else if (!email.validWithRegex(Regex.VALID_EMAIL_REGEX)) {
            emailEditTextLayout.error = getString(R.string.email_is_invalid)
            false
        } else {
            emailEditTextLayout.error = null
            true
        }
    }

    private fun handleEvents() {
        imageViewBack.setOnClickListener {
            hideKeyboard()
            parentFragmentManager.popBackStack()
        }
        forgotPasswordButton.setOnSafeClickListener {
            val validateEmail = validateEmail(emailEditText.text.toString())
            if (validateEmail) {
                progressBar.show()
                presenter.forgotPassword(emailEditText.text.toString())
            }
        }
    }

    companion object {
        fun newInstance() = ForgotPasswordFragment()
    }
}
