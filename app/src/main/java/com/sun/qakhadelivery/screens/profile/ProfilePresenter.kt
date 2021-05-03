package com.sun.qakhadelivery.screens.profile

import com.sun.qakhadelivery.data.repository.TokenRepository
import com.sun.qakhadelivery.data.repository.UserRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ProfilePresenter(
    private val tokenRepository: TokenRepository,
    private val userRepository: UserRepository
) : ProfileContract.Presenter {

    private var view: ProfileContract.View? = null
    private var compositeDisposable = CompositeDisposable()

    override fun getUser() {
        val disposable = userRepository.getUser(tokenRepository.getToken())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.onSuccessUser(it)
            }, {
                view?.onErrorUser(it.localizedMessage)
            })
        compositeDisposable.add(disposable)
    }

    override fun onStart() = Unit

    override fun onStop() {
        compositeDisposable.clear()
    }

    override fun setView(view: ProfileContract.View?) {
        this.view = view
    }
}
