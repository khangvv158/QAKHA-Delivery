package com.sun.qakhadelivery.screens.profile.update.email

import com.sun.qakhadelivery.data.repository.TokenRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable

class UpdateEmailPresenter(
    private val tokenRepository: TokenRepository
) : UpdateEmailContract.Presenter {

    private var view: UpdateEmailContract.View? = null
    private var compositeDisposable = CompositeDisposable()

    override fun onStart() = Unit

    override fun onStop() {
        compositeDisposable.clear()
    }

    override fun setView(view: UpdateEmailContract.View?) {
        this.view = view
    }
}
