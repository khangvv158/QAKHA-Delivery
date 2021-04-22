package com.sun.qakhadelivery.screens.forgotpassword.resetpassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.repository.SignRepositoryImpl
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefsImpl
import com.sun.qakhadelivery.extensions.makeText
import com.sun.qakhadelivery.utils.Regex
import com.sun.qakhadelivery.extensions.validWithRegex
import kotlinx.android.synthetic.main.fragment_reset_password.*
import kotlinx.android.synthetic.main.fragment_reset_password.imageViewBack

class ResetPasswordFragment : Fragment(), ResetPasswordContract.View {

    private val presenter by lazy {
        ResetPasswordPresenter(SignRepositoryImpl.getInstance(
                SharedPrefsImpl.getInstance(requireContext())
        ))
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reset_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.setView(this)
        handleEvents()
    }

    override fun onResetPasswordSuccess() {
        Toast.makeText(context, getString(R.string.create_a_new_password_successfully), Toast.LENGTH_LONG).show()
        parentFragmentManager.popBackStack()
    }

    override fun onResetPasswordFailure() {
        Toast.makeText(
                context,
                getString(R.string.code_not_valid_or_expired_try_generating_a_new_code),
                Toast.LENGTH_LONG
        ).show()
    }

    override fun onGeneratingCodeSuccess() {
        makeText(getString(R.string.generating_code))
    }

    override fun onGeneratingCodeFailure() {
    }

    private fun handleEvents() {
        imageViewBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        resetPasswordButton.setOnClickListener {
            val validatePassword = validatePassword(editTextNewPassword.text.toString())
            val validateVerificationCode = validateVerificationCode(
                    editTextVerificationCode.text.toString()
            )
            if (validatePassword && validateVerificationCode) {
                presenter.resetPassword(
                        editTextNewPassword.text.toString(),
                        editTextVerificationCode.text.toString().trim()
                )
            }
        }
        textViewGeneratingCode.setOnClickListener {
            arguments?.getString(BUNDLE_EMAIL)?.let {
                presenter.generatingCode(it)
            }
        }
    }

    private fun validatePassword(password: String): Boolean {
        return if (password.isEmpty()) {
            editTextLayoutNewPassword.error = getString(R.string.password_cant_be_blank)
            false
        } else if (!password.validWithRegex(Regex.VALID_PASSWORD_REGEX)) {
            editTextLayoutNewPassword.error =
                    getString(R.string.description_validate_password)
            false
        } else {
            editTextLayoutNewPassword.error = null
            true
        }
    }

    private fun validateVerificationCode(verificationCode: String): Boolean {
        return if (verificationCode.isEmpty()) {
            editTextLayoutVerificationCode.error = getString(R.string.token_not_present)
            false
        } else {
            editTextLayoutVerificationCode?.error = null
            true
        }
    }

    companion object {

        private const val BUNDLE_EMAIL = "BUNDLE_EMAIL"

        fun newInstance(email: String) = ResetPasswordFragment().apply {
            arguments = bundleOf(BUNDLE_EMAIL to email)
        }
    }
}
