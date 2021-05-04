package com.sun.qakhadelivery.screens.search

import com.sun.qakhadelivery.data.repository.PartnerRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class SearchPresenter(private val partnerRepository: PartnerRepository) : SearchContract.Presenter {

    private var view: SearchContract.View? = null
    private val compositeDisposable = CompositeDisposable()

    override fun getPartners() {
        val disposable = partnerRepository.getPartners()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.getPartnersSuccess(it)
            }, {
            })
        compositeDisposable.add(disposable)
    }

    override fun onStart() {
    }

    override fun onStop() {
        compositeDisposable.clear()
    }

    override fun setView(view: SearchContract.View?) {
        this.view = view
    }
}
