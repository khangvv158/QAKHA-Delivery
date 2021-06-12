package com.sun.qakhadelivery.screens.signup.activated

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.model.Event
import com.sun.qakhadelivery.data.repository.SignRepositoryImpl
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefsImpl
import com.sun.qakhadelivery.data.source.remote.schema.request.EmailRequest
import com.sun.qakhadelivery.extensions.gone
import com.sun.qakhadelivery.extensions.makeText
import com.sun.qakhadelivery.extensions.setOnSafeClickListener
import com.sun.qakhadelivery.extensions.show
import kotlinx.android.synthetic.main.activated_fragment.*
import org.greenrobot.eventbus.EventBus

class ActivatedFragment : Fragment(), ActivatedContract.View {

    private val presenter by lazy {
        ActivatedPresenter(
            SignRepositoryImpl.getInstance(SharedPrefsImpl.getInstance(requireContext()))
        )
    }
    private var emailRequest: EmailRequest? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activated_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        handleEvents()
    }

    override fun onActivateAccountSuccess() {
        progressBar.show()
        Toast.makeText(
            requireContext(),
            getString(R.string.content_activate_success),
            Toast.LENGTH_LONG
        ).show()
        progressBar.gone()
        EventBus.getDefault().post(Event(EVENT_ACTIVATE_SUCCESS, EVENT_ACTIVATE_SUCCESS))
        parentFragmentManager.popBackStack()
    }

    override fun onActivateAccountFailure() {
        progressBar.show()
        makeText(getString(R.string.content_activate_failure))
    }

    override fun onResendCodeActivateSuccess() {
        progressBar.gone()
        makeText(getString(R.string.generating_code))
    }

    override fun onResendCodeActivateFailure() {
        progressBar.gone()
    }

    override fun onError(message: String) {
        progressBar.gone()
        makeText(message)
    }

    override fun onStop() {
        presenter.onStop()
        super.onStop()
    }

    private fun initViews() {
        arguments?.getParcelable<EmailRequest>(ARGUMENT_EMAIL)?.let {
            emailRequest = it
        }
        presenter.setView(this)
    }

    private fun handleEvents() {
        activateButton.setOnSafeClickListener {
            progressBar.show()
            presenter.activateAccount(editTextActivateCode.text.toString().trim())
        }
        imageViewBack.setOnSafeClickListener {
            EventBus.getDefault().post(Event(EVENT_BUS_FRESH, EVENT_BUS_FRESH))
            parentFragmentManager.popBackStack()
        }
        textViewGeneratingCode.setOnSafeClickListener {
            emailRequest?.let { email ->
                presenter.resendCodeActivate(email)
                progressBar.show()
            }
        }
    }

    companion object {
        private const val ARGUMENT_EMAIL = "ARGUMENT_EMAIL"
        const val EVENT_ACTIVATE_SUCCESS = "EVENT_ACTIVATE_SUCCESS"
        const val EVENT_BUS_FRESH = "EVENT_BUS_FRESH"

        fun newInstance(emailRequest: EmailRequest?) = ActivatedFragment().apply {
            arguments = bundleOf(ARGUMENT_EMAIL to emailRequest)
        }
    }
}
