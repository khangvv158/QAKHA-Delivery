package com.sun.qakhadelivery.screens.home

import com.sun.qakhadelivery.data.repository.PartnerRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class HomePresenter(private val partnerRepository: PartnerRepository) : HomeContract.Presenter {

    private var view: HomeContract.View? = null
    private val compositeDisposable = CompositeDisposable()

    override fun getTypes() {
        val disposable = partnerRepository.getTypes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.onGetTypesSuccess(it)
            }, {
                view?.onError(it.localizedMessage)
            })
        compositeDisposable.add(disposable)
    }

    override fun onStart() = Unit

    override fun onStop() {
        compositeDisposable.clear()
        view = null
    }

    override fun setView(view: HomeContract.View?) {
        this.view = view
    }
}
