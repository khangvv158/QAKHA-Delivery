package com.sun.qakhadelivery.screens.navigate.setting.changepassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.repository.TokenRepositoryImpl
import com.sun.qakhadelivery.data.repository.UserRepositoryImpl
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefsImpl
import com.sun.qakhadelivery.data.source.remote.schema.request.ChangePasswordRequest
import com.sun.qakhadelivery.extensions.*
import com.sun.qakhadelivery.utils.Regex
import kotlinx.android.synthetic.main.fragment_change_password.*
import kotlinx.android.synthetic.main.fragment_change_password.imageViewBack

class ChangePasswordFragment : Fragment(), ChangePasswordContract.View {

    private val presenter by lazy {
        ChangePasswordPresenter(
            UserRepositoryImpl.getInstance(),
            TokenRepositoryImpl.getInstance(SharedPrefsImpl.getInstance(requireContext()))
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_change_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        handleEvents()
    }

    override fun onError(message: String) {
        makeText(message)
        loadingProgress.gone()
    }

    override fun onChangePasswordSuccess() {
        makeText(getString(R.string.change_password_successfully))
        clearText()
        loadingProgress.gone()
        parentFragmentManager.popBackStack()
    }

    override fun onChangePasswordSuccessFailure(message: String) {
        loadingProgress.gone()
        if (message.contains("Current password don't match")) {
            editTextLayoutCurrentPassword.error = getString(R.string.current_password_dont_match)
        }
    }

    private fun initViews() {
        presenter.setView(this)
    }

    private fun handleEvents() {
        imageViewBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        changePasswordButton.setOnClickListener {
            val checkPassword = validatePassword(editTextNewPassword.text.toString())
            val checkPasswordConfirmation =
                validatePasswordConfirmation(editTextConfirmationPassword.text.toString())
            if (checkPassword && checkPasswordConfirmation) {
                hideKeyboard()
                loadingProgress.show()
                presenter.changePassword(
                    ChangePasswordRequest(
                        editTextCurrentPassword.text.toString(),
                        editTextNewPassword.text.toString(),
                        editTextConfirmationPassword.text.toString()
                    )
                )
            }
        }
    }

    private fun clearText() {
        editTextLayoutCurrentPassword.error = null
        editTextCurrentPassword.text = null
        editTextLayoutNewPassword.error = null
        editTextNewPassword.text = null
        editTextLayoutConfirmationPassword.error = null
        editTextConfirmationPassword.text = null
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

    private fun validatePasswordConfirmation(passwordConfirmation: String): Boolean {
        return when {
            passwordConfirmation.isEmpty() -> {
                editTextLayoutConfirmationPassword.error =
                    getString(R.string.password_cant_be_blank)
                false
            }
            passwordConfirmation != editTextNewPassword.text.toString() -> {
                editTextLayoutConfirmationPassword.error =
                    getString(R.string.description_validate_confirmation_password)
                false
            }
            else -> {
                editTextLayoutConfirmationPassword.error = null
                true
            }
        }
    }

    companion object {
        fun newInstance() = ChangePasswordFragment()
    }

}
