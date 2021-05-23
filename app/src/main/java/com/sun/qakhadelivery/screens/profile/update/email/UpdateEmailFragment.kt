package com.sun.qakhadelivery.screens.profile.update.email

import android.content.res.ColorStateList
import android.os.Bundle
import android.os.CountDownTimer
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
import com.sun.qakhadelivery.data.source.remote.schema.request.UpdateEmail
import com.sun.qakhadelivery.data.source.remote.schema.response.MessageResponse
import com.sun.qakhadelivery.data.source.remote.schema.response.VerifyEmail
import com.sun.qakhadelivery.extensions.*
import com.sun.qakhadelivery.screens.profile.ProfileFragment
import com.sun.qakhadelivery.utils.EditTextObservable
import com.sun.qakhadelivery.utils.Regex
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_update_email.*
import org.greenrobot.eventbus.EventBus
import java.util.concurrent.TimeUnit

class UpdateEmailFragment : Fragment(), UpdateEmailContract.View {

    private val presenter by lazy {
        UpdateEmailPresenter(
            TokenRepositoryImpl.getInstance(
                SharedPrefsImpl.getInstance(requireContext())
            ),
            UserRepositoryImpl.getInstance()
        )
    }
    private var countDownTimer: CountDownTimer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_update_email, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        handleEvents()
    }

    override fun onStart() {
        super.onStart()
        presenter.setView(this@UpdateEmailFragment)
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer = null
    }

    override fun onSuccessSendCodeEmail(user: User) {
        enableInteraction()
        sendCodeButton.disable()
        emailEditText.disable()
        verifyTextInputLayout.show()
        verifyButton.show()
        sendCodeButton.backgroundTintList =
            ColorStateList.valueOf(resources.getColor(R.color.colorGrayBombay))
        countDownTimer = object : CountDownTimer(45000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                sendCodeButton?.text =
                    String.format(getString(R.string.send_counter), millisUntilFinished / 1000)
            }

            override fun onFinish() {
                sendCodeButton?.text = getString(R.string.send)
                sendCodeButton.backgroundTintList =
                    ColorStateList.valueOf(resources.getColor(R.color.colorRedPomegranate))
                sendCodeButton?.enable()
            }
        }.start()
    }

    override fun onSuccessVerifyEmail(messageResponse: MessageResponse) {
        enableInteraction()
        makeText(getString(R.string.update_email_success))
        EventBus.getDefault().post(Refresh(this::class.java, ProfileFragment::class.java))
        parentFragmentManager.popBackStack()
    }

    override fun onErrorSendCodeEmail(exception: String) {
        enableInteraction()
        makeText(exception)
    }

    override fun onErrorVerifyEmail(exception: String) {
        enableInteraction()
        makeText(exception)
    }

    private fun initViews() {
        arguments?.getParcelable<User>(BUNDLE_USER)?.let {
            emailEditText.setText(it.email)
        }
    }

    private fun handleEvents() {
        imageViewBack.setOnSafeClickListener {
            parentFragmentManager.popBackStack()
        }
        EditTextObservable.fromView(emailEditText)
            .debounce(500, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { text ->
                validateEmail(text).also { isValid ->
                    sendCodeButton.setOnSafeClickListener {
                        if (isValid) {
                            presenter.sendCodeEmail(
                                UpdateEmail(text.trim())
                            )
                            disableInteraction()
                        }
                    }
                }
            }
        verifyButton.setOnSafeClickListener {
            presenter.verifyEmail(
                VerifyEmail(
                    emailEditText.text.toString(),
                    verifyEditText.text.toString().trim()
                )
            )
            disableInteraction()
        }
    }

    private fun validateEmail(email: String): Boolean {
        return if (email.isEmpty()) {
            textInputLayout.error = getString(R.string.email_cant_be_blank)
            false
        } else if (!email.validWithRegex(Regex.VALID_EMAIL_REGEX)) {
            textInputLayout.error = getString(R.string.email_is_invalid)
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

        fun newInstance(user: User?) = UpdateEmailFragment().apply {
            arguments = Bundle().apply {
                putParcelable(BUNDLE_USER, user)
            }
        }
    }
}
