package com.sun.qakhadelivery.screens.signedin

import com.sun.qakhadelivery.data.repository.UserRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class SignedInPresenter(
        private val userRepository: UserRepository
) : SignedInContract.Presenter {

    private var view: SignedInContract.View? = null
    private var compositeDisposable = CompositeDisposable()

    override fun getUser() {
        val disposable = userRepository.getUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.onGetUserSuccess(it)
                }, {
                    view?.onError(it.localizedMessage)
                })
        compositeDisposable.add(disposable)
    }

    override fun signOut() {
        userRepository.signOut()
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
