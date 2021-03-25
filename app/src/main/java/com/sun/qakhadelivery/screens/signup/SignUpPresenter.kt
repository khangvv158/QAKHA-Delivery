package com.sun.qakhadelivery.screens.signup

import com.google.gson.Gson
import com.sun.qakhadelivery.data.model.MessageError
import com.sun.qakhadelivery.data.repository.SignRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException

class SignUpPresenter(private val signRepository: SignRepository) : SignUpContract.Presenter {

    private var view: SignUpContract.View? = null
    private var compositeDisposable = CompositeDisposable()

    override fun signUp(
            email: String,
            password: String,
            passwordConfirmation: String,
            phoneNumber: String,
            name: String) {
        val disposable = signRepository.signUp(
                email,
                password,
                passwordConfirmation,
                phoneNumber,
                name
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.onSignUpSuccess()
                }, {
                    if (it is HttpException) {
                        try {
                            view?.onSignUpFailure(Gson().fromJson(
                                    it.response()?.errorBody()?.string(),
                                    MessageError::class.java
                            ).message)
                        } catch (e: Exception) {
                            view?.onError(it.localizedMessage)
                        }
                    }
                })
        compositeDisposable.add(disposable)
    }

    override fun onStart() = Unit

    override fun onStop() {
        compositeDisposable.clear()
    }

    override fun setView(view: SignUpContract.View?) {
        this.view = view
    }
}
