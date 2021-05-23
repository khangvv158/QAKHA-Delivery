package com.sun.qakhadelivery.screens.profile.update.phone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.model.Refresh
import com.sun.qakhadelivery.data.model.User
import com.sun.qakhadelivery.data.repository.TokenRepositoryImpl
import com.sun.qakhadelivery.data.repository.UserRepositoryImpl
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefsImpl
import com.sun.qakhadelivery.data.source.remote.schema.request.UpdatePhone
import com.sun.qakhadelivery.extensions.*
import com.sun.qakhadelivery.screens.profile.ProfileFragment
import com.sun.qakhadelivery.utils.EditTextObservable
import com.sun.qakhadelivery.utils.Regex
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_update_phone.*
import org.greenrobot.eventbus.EventBus
import java.util.concurrent.TimeUnit

class UpdatePhoneFragment : Fragment(), UpdatePhoneContract.View {

    private val presenter by lazy {
        UpdatePhonePresenter(
            TokenRepositoryImpl.getInstance(
                SharedPrefsImpl.getInstance(requireContext())
            ),
            UserRepositoryImpl.getInstance()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_update_phone, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        handleEvents()
    }

    override fun onStart() {
        super.onStart()
        presenter.setView(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun onSuccessPhone(user: User) {
        enableInteraction()
        makeText(getString(R.string.update_phone_success))
        EventBus.getDefault().post(Refresh(this::class.java, ProfileFragment::class.java))
        parentFragmentManager.popBackStack()
    }

    override fun onErrorPhone(exception: String) {
        enableInteraction()
        makeText(exception)
    }

    private fun initViews() {
        arguments?.getParcelable<User>(BUNDLE_USER)?.let {
            phoneNumberEditText.setText(it.phoneNumber)
        }
    }

    private fun handleEvents() {
        imageViewBack.setOnSafeClickListener {
            parentFragmentManager.popBackStack()
        }
        EditTextObservable.fromView(phoneNumberEditText)
            .debounce(500, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { text ->
                validatePhoneNumber(text).also { isValid ->
                    sendCodeButton.setOnSafeClickListener {
                        if (isValid) {
                            presenter.updatePhone(
                                UpdatePhone(text.trim())
                            )
                            disableInteraction()
                        }
                    }
                }
            }
    }

    private fun validatePhoneNumber(phoneNumber: String): Boolean {
        return if (phoneNumber.isEmpty()) {
            textInputLayout.error = getString(R.string.phone_number_is_too_short)
            false
        } else if (!phoneNumber.validWithRegex(Regex.VALID_PHONE_REGEX)) {
            textInputLayout.error = getString(R.string.phone_number_is_invalid)
            false
        } else {
            textInputLayout.error = null
            true
        }
    }

    private fun disableInteraction() {
        loadingProgress.show()
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    private fun enableInteraction() {
        loadingProgress.gone()
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    companion object {
        private const val BUNDLE_USER = "BUNDLE_USER"

        fun newInstance(user: User?) = UpdatePhoneFragment().apply {
            arguments = Bundle().apply {
                putParcelable(BUNDLE_USER, user)
            }
        }
    }
}
