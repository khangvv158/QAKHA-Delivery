package com.sun.qakhadelivery.screens.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.repository.SignRepositoryImpl
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefsImpl
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUpFragment : Fragment(), SignUpContract.View {

    private val presenter by lazy {
        SignUpPresenter(SignRepositoryImpl.getInstance(
                SharedPrefsImpl.getInstance(requireContext())
        ))
    }

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

    override fun onSignUpSuccess() {
        Toast.makeText(context, getString(R.string.sign_up_success), Toast.LENGTH_LONG).show()
    }

    override fun onSignUpFailure(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun onError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun handleEvents() {
        imageViewBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        signUpButton.setOnClickListener {
            presenter.signUp(emailEditText.text.toString(),
                    passwordEditText.text.toString(),
                    confirmPasswordEditText.text.toString(),
                    phoneNumberEditText.text.toString(),
                    nameEditText.text.toString()
            )
        }
    }

    companion object {
        fun newInstance() = SignUpFragment()
    }
}
