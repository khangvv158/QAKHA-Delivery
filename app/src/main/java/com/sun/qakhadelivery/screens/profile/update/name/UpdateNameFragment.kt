package com.sun.qakhadelivery.screens.profile.update.name

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
import com.sun.qakhadelivery.data.source.remote.schema.request.UpdateUsername
import com.sun.qakhadelivery.extensions.gone
import com.sun.qakhadelivery.extensions.makeText
import com.sun.qakhadelivery.extensions.setOnSafeClickListener
import com.sun.qakhadelivery.extensions.show
import com.sun.qakhadelivery.screens.profile.ProfileFragment
import com.sun.qakhadelivery.utils.EditTextObservable
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_update_name.*
import org.greenrobot.eventbus.EventBus
import java.util.concurrent.TimeUnit

class UpdateNameFragment : Fragment(), UpdateNameContract.View {

    private val presenter by lazy {
        UpdateNamePresenter(
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
        return inflater.inflate(R.layout.fragment_update_name, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        handleEvent()
    }

    override fun onStart() {
        super.onStart()
        presenter.setView(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun onSuccessUsername(user: User) {
        enableInteraction()
        makeText(getString(R.string.update_username_success))
        EventBus.getDefault().post(Refresh(this::class.java, ProfileFragment::class.java))
        parentFragmentManager.popBackStack()
    }

    override fun onErrorUsername(exception: String) {
        enableInteraction()
        makeText(exception)
    }

    private fun initViews() {
        arguments?.getParcelable<User>(BUNDLE_USER)?.let {
            nameEditText.setText(it.name)
        }
    }

    private fun handleEvent() {
        imageViewBack.setOnSafeClickListener {
            parentFragmentManager.popBackStack()
        }
        EditTextObservable.fromView(nameEditText)
            .debounce(500, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { text ->
                validateUsername(text).also { isValid ->
                    sendCodeButton.setOnSafeClickListener {
                        if (isValid) {
                            presenter.updateUsername(
                                UpdateUsername(text.trim())
                            )
                            disableInteraction()
                        }
                    }
                }
            }
    }

    private fun validateUsername(name: String): Boolean {
        return if (name.isEmpty()) {
            textInputLayout.error = getString(R.string.name_cant_be_blank)
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

        fun newInstance(user: User?) = UpdateNameFragment().apply {
            arguments = Bundle().apply {
                putParcelable(BUNDLE_USER, user)
            }
        }
    }
}
