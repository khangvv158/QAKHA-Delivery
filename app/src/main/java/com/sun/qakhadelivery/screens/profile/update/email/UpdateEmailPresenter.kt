package com.sun.qakhadelivery.screens.profile.update.email

import com.google.gson.Gson
import com.sun.qakhadelivery.data.repository.TokenRepository
import com.sun.qakhadelivery.data.repository.UserRepository
import com.sun.qakhadelivery.data.source.remote.schema.request.UpdateEmail
import com.sun.qakhadelivery.data.source.remote.schema.response.MessageResponse
import com.sun.qakhadelivery.data.source.remote.schema.response.VerifyEmail
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException

class UpdateEmailPresenter(
    private val tokenRepository: TokenRepository,
    private val userRepository: UserRepository
) : UpdateEmailContract.Presenter {

    private var view: UpdateEmailContract.View? = null
    private var compositeDisposable = CompositeDisposable()

    override fun sendCodeEmail(updateEmail: UpdateEmail) {
        val disposable = userRepository.updateEmail(updateEmail, tokenRepository.getToken())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.onSuccessSendCodeEmail(it)
            }, {
                if (it is HttpException) {
                    try {
                        view?.onErrorSendCodeEmail(
                            Gson().fromJson(
                                it.response()?.errorBody()?.string(),
                                MessageResponse::class.java
                            ).message
                        )
                    } catch (e: Exception) {
                        view?.onErrorSendCodeEmail(it.localizedMessage)
                    }
                }
            })
        compositeDisposable.add(disposable)
    }

    override fun verifyEmail(verifyEmail: VerifyEmail) {
        val disposable = userRepository.verifyEmail(verifyEmail, tokenRepository.getToken())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.onSuccessVerifyEmail(it)
            }, {
                if (it is HttpException) {
                    try {
                        view?.onErrorVerifyEmail(
                            Gson().fromJson(
                                it.response()?.errorBody()?.string(),
                                MessageResponse::class.java
                            ).message
                        )
                    } catch (e: Exception) {
                        view?.onErrorVerifyEmail(it.localizedMessage)
                    }
                }
            })
        compositeDisposable.add(disposable)
    }

    override fun onStart() = Unit

    override fun onStop() {
        compositeDisposable.clear()
    }

    override fun setView(view: UpdateEmailContract.View?) {
        this.view = view
    }
}
