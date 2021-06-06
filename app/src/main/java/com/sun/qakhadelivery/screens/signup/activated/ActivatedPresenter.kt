package com.sun.qakhadelivery.screens.signup.activated

import com.sun.qakhadelivery.data.repository.SignRepository
import com.sun.qakhadelivery.data.source.remote.schema.request.ActivateRequest
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ActivatedPresenter(private val signRepository: SignRepository) : ActivatedContract.Presenter {

    private var view: ActivatedContract.View? = null
    private val compositeDisposable = CompositeDisposable()

    override fun activateAccount(codeActivate: String) {
        val disposable = signRepository.activateAccount(ActivateRequest(codeActivate))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.onActivateAccountSuccess()
            }, {
                view?.onActivateAccountFailure()
            })
        compositeDisposable.add(disposable)
    }

    override fun onStart() = Unit

    override fun onStop() {
        compositeDisposable.clear()
        view = null
    }

    override fun setView(view: ActivatedContract.View?) {
        this.view = view
    }
}
