package com.sun.qakhadelivery.screens.signedin

import com.sun.qakhadelivery.data.repository.TokenRepository
import com.sun.qakhadelivery.data.repository.UserRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class SignedInPresenter(
    private val userRepository: UserRepository,
    private val tokenRepository: TokenRepository
) : SignedInContract.Presenter {

    private var view: SignedInContract.View? = null
    private var compositeDisposable = CompositeDisposable()

    override fun getUser() {
        val disposable = userRepository.getUser(tokenRepository.getToken())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.onGetUserSuccess(it)
            }, {
                tokenRepository.clearToken()
                view?.onGetUserFailure()
            })
        compositeDisposable.add(disposable)
    }

    override fun signOut() {
        tokenRepository.clearToken()
        view?.onSignOutSuccess()
    }

    override fun onStart() = Unit

    override fun onStop() {
        compositeDisposable.clear()
    }

    override fun setView(view: SignedInContract.View?) {
        this.view = view
    }
}
