package com.sun.qakhadelivery.screens.home.tabs.all

import com.sun.qakhadelivery.data.repository.PartnerRepository
import com.sun.qakhadelivery.data.source.remote.schema.request.TypeRequest
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class AllPresenter(private val partnerRepository: PartnerRepository) : AllContract.Presenter {

    private var view: AllContract.View? = null
    private val compositeDisposable = CompositeDisposable()

    override fun getPartners() {
        val disposable = partnerRepository.getPartners()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.onSuccessGetPartners(it)
            }, {
                view?.onErrorGetPartners(it.message.toString())
            })
        compositeDisposable.add(disposable)
    }

    override fun getPartnersByIdType(idType: Int) {
        val disposable = partnerRepository.getPartnersByIdType(idType)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.onSuccessGetPartnersById(it)
            }, {
                view?.onErrorGetPartners(it.message.toString())
            })
        compositeDisposable.add(disposable)
    }

    override fun onStart() = Unit

    override fun onStop() {
        compositeDisposable.clear()
    }

    override fun setView(view: AllContract.View?) {
        this.view = view
    }
}
