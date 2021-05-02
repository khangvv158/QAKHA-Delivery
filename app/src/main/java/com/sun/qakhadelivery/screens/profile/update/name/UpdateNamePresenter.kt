package com.sun.qakhadelivery.screens.profile.update.name

import com.sun.qakhadelivery.data.repository.TokenRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable

class UpdateNamePresenter(
    private val tokenRepository: TokenRepository
) : UpdateNameContract.Presenter {

    private var view: UpdateNameContract.View? = null
    private var compositeDisposable = CompositeDisposable()

    override fun onStart() = Unit

    override fun onStop() {
        compositeDisposable.clear()
    }

    override fun setView(view: UpdateNameContract.View?) {
        this.view = view
    }
}
