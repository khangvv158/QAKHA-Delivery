package com.sun.qakhadelivery.screens.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.repository.SignRepositoryImpl
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefsImpl
import com.sun.qakhadelivery.extensions.*
import com.sun.qakhadelivery.screens.signup.activated.ActivatedFragment
import com.sun.qakhadelivery.screens.signup.activated.ActivatedSuccessListener
import com.sun.qakhadelivery.utils.EditTextObservable
import com.sun.qakhadelivery.utils.Regex
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_sign_up.*
import java.util.concurrent.TimeUnit

class SignUpFragment : Fragment(), SignUpContract.View, ActivatedSuccessListener {

    private val presenter by lazy {
        SignUpPresenter(
            SignRepositoryImpl.getInstance(
                SharedPrefsImpl.getInstance(requireContext())
            )
        )
    }
    private var emailIsExist = false
    private var phoneNumberIsExist = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.setView(this)
        handleEvents()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun onActivatedSuccess() {
        makeText(getString(R.string.content_activate_success))
        clearEditText()
        parentFragmentManager.popBackStack()
    }

    override fun onSignUpSuccess() {
        clearEditText()
        addFragmentBackStack(ActivatedFragment.newInstance().apply {
            registerListener(this@SignUpFragment)
        }, R.id.containerView)
        loadingProgress.gone()
    }

    override fun onSignUpFailure(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        loadingProgress.gone()
    }

    override fun onCheckEmailIsExistSuccess() {
        emailIsExist = true
    }

    override fun onCheckEmailIsExistFailure() {
        emailTextInputLayout?.error = getString(R.string.email_is_already_exists_in_database)
        emailIsExist = false
    }

    override fun onCheckPhoneNumberIsExistSuccess() {
        phoneNumberIsExist = true
    }

    override fun onCheckPhoneNumberIsExistFailure() {
        phoneNumberTextInputLayout?.error =
            getString(R.string.Phone_umber_is_already_exists_in_database)
        phoneNumberIsExist = false
    }

    override fun onError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        loadingProgress.gone()
    }

    private fun handleEvents() {
        imageViewBack.setOnClickListener {
            hideKeyboard()
            parentFragmentManager.popBackStack()
        }
        signUpButton.setOnClickListener {
            val checkEmail = validateEmail(emailEditText.text.toString())
            val checkPassword = validatePassword(passwordEditText.text.toString())
            val checkPasswordConfirmation =
                validatePasswordConfirmation(confirmPasswordEditText.text.toString())
            val checkUserName = validateUsername(nameEditText.text.toString())
            val checkPhoneNumber = validatePhoneNumber(phoneNumberEditText.text.toString())
            if (checkEmail &&
                checkPassword &&
                checkPasswordConfirmation &&
                checkUserName &&
                checkPhoneNumber &&
                emailIsExist &&
                phoneNumberIsExist
            ) {
                hideKeyboard()
                presenter.signUp(
                    emailEditText.text.toString(),
                    passwordEditText.text.toString(),
                    confirmPasswordEditText.text.toString(),
                    phoneNumberEditText.text.toString(),
                    nameEditText.text.toString()
                )
                emailIsExist = false
                phoneNumberIsExist = false
                loadingProgress.show()
            }
        }
        containerViewSignUp.setOnClickListener {
            hideKeyboard()
        }
        handleEventsAfterTextChanged()
        handleEventsKeyBoard()
        activateTextView.setOnClickListener {
            addFragmentBackStack(ActivatedFragment.newInstance().apply {
                registerListener(this@SignUpFragment)
            }, R.id.containerView)
        }
    }

    private fun handleEventsAfterTextChanged() {
        EditTextObservable.fromView(emailEditText)
            .debounce(1000, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { text ->
                validateEmail(text).also { isValid ->
                    if (isValid) {
                        presenter.checkEmailIsExist(text)
                    }
                }
            }
        EditTextObservable.fromView(phoneNumberEditText)
            .debounce(1000, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { text ->
                validatePhoneNumber(text).also { isValid ->
                    if (isValid) {
                        presenter.checkPhoneNumberIsExist(text)
                    }
                }
            }
        EditTextObservable.fromView(passwordEditText)
            .debounce(500, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { text ->
                validatePassword(text)
            }
        EditTextObservable.fromView(confirmPasswordEditText)
            .debounce(500, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { text ->
                validatePasswordConfirmation(text)
            }
        EditTextObservable.fromView(nameEditText)
            .debounce(500, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { text ->
                validateUsername(text)
            }
    }

    private fun handleEventsKeyBoard() {
        emailEditText.setOnEditorActionListener { view, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO ||
                actionId == EditorInfo.IME_ACTION_DONE
            ) {
                if (validateEmail(view.text.toString())) {
                    presenter.checkEmailIsExist(view.text.toString())
                    true
                }
            }
            false
        }
        phoneNumberEditText.setOnEditorActionListener { view, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO ||
                actionId == EditorInfo.IME_ACTION_DONE
            ) {
                if (validatePhoneNumber(view.text.toString())) {
                    presenter.checkPhoneNumberIsExist(view.text.toString())
                    true
                }
            }
            false
        }
    }

    private fun validateEmail(email: String): Boolean {
        return if (email.isEmpty()) {
            emailTextInputLayout?.error = getString(R.string.email_cant_be_blank)
            false
        } else if (!email.validWithRegex(Regex.VALID_EMAIL_REGEX)) {
            emailTextInputLayout?.error = getString(R.string.email_is_invalid)
            false
        } else {
            emailTextInputLayout?.error = null
            true
        }
    }

    private fun validatePassword(password: String): Boolean {
        return if (password.isEmpty()) {
            passwordTextInputLayout?.error = getString(R.string.password_cant_be_blank)
            false
        } else if (!password.validWithRegex(Regex.VALID_PASSWORD_REGEX)) {
            passwordTextInputLayout?.error =
                getString(R.string.description_validate_password)
            false
        } else {
            passwordTextInputLayout?.error = null
            true
        }
    }

    private fun validatePasswordConfirmation(passwordConfirmation: String): Boolean {
        return when {
            passwordConfirmation.isEmpty() -> {
                confirmPasswordTextInputLayout?.error = getString(R.string.password_cant_be_blank)
                false
            }
            passwordConfirmation != passwordEditText?.text.toString() -> {
                confirmPasswordTextInputLayout?.error =
                    getString(R.string.description_validate_confirmation_password)
                false
            }
            else -> {
                confirmPasswordTextInputLayout?.error = null
                true
            }
        }
    }

    private fun validateUsername(name: String): Boolean {
        return if (name.isEmpty()) {
            nameTextInputLayout?.error = getString(R.string.name_cant_be_blank)
            false
        } else {
            nameTextInputLayout?.error = null
            true
        }
    }

    private fun validatePhoneNumber(phoneNumber: String): Boolean {
        return if (phoneNumber.isEmpty()) {
            phoneNumberTextInputLayout?.error =
                getString(R.string.phone_number_is_too_short)
            false
        } else if (!phoneNumber.validWithRegex(Regex.VALID_PHONE_REGEX)) {
            phoneNumberTextInputLayout?.error = getString(R.string.phone_number_is_invalid)
            false
        } else {
            phoneNumberTextInputLayout?.error = null
            true
        }
    }

    private fun clearEditText() {
        emailEditText?.text = null
        emailTextInputLayout?.error = null
        passwordEditText?.text = null
        passwordTextInputLayout?.error = null
        confirmPasswordEditText?.text = null
        confirmPasswordTextInputLayout?.error = null
        nameEditText?.text = null
        nameTextInputLayout?.error = null
        phoneNumberEditText?.text = null
        phoneNumberTextInputLayout?.error = null
    }

    companion object {
        fun newInstance() = SignUpFragment()
    }
}
