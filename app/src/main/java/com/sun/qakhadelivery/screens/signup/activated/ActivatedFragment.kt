package com.sun.qakhadelivery.screens.signup.activated

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.repository.SignRepositoryImpl
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefsImpl
import com.sun.qakhadelivery.extensions.makeText
import kotlinx.android.synthetic.main.activated_fragment.*

class ActivatedFragment : Fragment(), ActivatedContract.View {

    private val presenter by lazy {
        ActivatedPresenter(
            SignRepositoryImpl.getInstance(SharedPrefsImpl.getInstance(requireContext()))
        )
    }
    private var onActivatedSuccess : ActivatedSuccessListener? = null

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
        onActivatedSuccess?.onActivatedSuccess()
        parentFragmentManager.popBackStack()
    }

    override fun onActivateAccountFailure() {
        makeText(getString(R.string.content_activate_failure))
    }

    override fun onStop() {
        presenter.onStop()
        super.onStop()
    }

    fun registerListener(onActivatedSuccessListener: ActivatedSuccessListener){
        this.onActivatedSuccess = onActivatedSuccessListener
    }

    private fun initViews() {
        presenter.setView(this)
    }

    private fun handleEvents() {
        activateButton.setOnClickListener {
            presenter.activateAccount(editTextActivateCode.text.toString().trim())
        }
        imageViewBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    companion object {
        fun newInstance() = ActivatedFragment()
    }
}
