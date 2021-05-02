package com.sun.qakhadelivery.screens.profile.update.phone

import com.sun.qakhadelivery.data.repository.TokenRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable

class UpdatePhonePresenter(
    private val tokenRepository: TokenRepository
) : UpdatePhoneContract.Presenter {

    private var view: UpdatePhoneContract.View? = null
    private var compositeDisposable = CompositeDisposable()

    override fun onStart() = Unit

    override fun onStop() {
        compositeDisposable.clear()
    }

    override fun setView(view: UpdatePhoneContract.View?) {
        this.view = view
    }
}
