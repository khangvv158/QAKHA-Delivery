package com.sun.qakhadelivery.screens.profile

import com.sun.qakhadelivery.data.repository.TokenRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable

class ProfilePresenter(
    private val tokenRepository: TokenRepository
) : ProfileContract.Presenter {

    private var view: ProfileContract.View? = null
    private var compositeDisposable = CompositeDisposable()

    override fun onStart() = Unit

    override fun onStop() {
        compositeDisposable.clear()
    }

    override fun setView(view: ProfileContract.View?) {
        this.view = view
    }
}
