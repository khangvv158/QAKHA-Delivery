package com.sun.qakhadelivery.screens.signin

import com.google.gson.Gson
import com.sun.qakhadelivery.data.model.MessageResponse
import com.sun.qakhadelivery.data.repository.SignRepository
import com.sun.qakhadelivery.utils.Constants
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException

class SignInPresenter(private val repository: SignRepository) : SignInContract.Presenter {

    private var view: SignInContract.View? = null
    private val compositeDisposable = CompositeDisposable()

    override fun signIn(email: String, password: String) {
        val disposable = repository.signIn(email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.role == Constants.ROLE_MEMBER) {
                    view?.onSignInSuccess()
                } else {
                    view?.onSignInRoleFailure()
                }
            }, {
                if (it is HttpException) {
                    try {
                        view?.onSignInFailure(
                            Gson().fromJson(
                                it.response()?.errorBody()?.string(),
                                MessageResponse::class.java
                            ).message
                        )
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

    override fun setView(view: SignInContract.View?) {
        this.view = view
    }
}
