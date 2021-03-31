package com.sun.qakhadelivery.screens.forgotpassword

import com.sun.qakhadelivery.data.repository.SignRepository
import com.sun.qakhadelivery.data.source.remote.schema.request.EmailRequest
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ForgotPasswordPresenter(private val signRepository: SignRepository) : ForgotPasswordContract.Presenter {

    private var view: ForgotPasswordContract.View? = null
    private val compositeDisposable = CompositeDisposable()

    override fun forgotPassword(email: String) {
        val disposable = signRepository.forgotPassword(EmailRequest(email))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.onForgotPasswordSuccess(email)
                }, {
                    view?.onForgotPasswordFailure()
                })
        compositeDisposable.add(disposable)
    }

    override fun onStart() = Unit

    override fun onStop() {
        compositeDisposable.clear()
    }

    override fun setView(view: ForgotPasswordContract.View?) {
        this.view = view
    }
}
