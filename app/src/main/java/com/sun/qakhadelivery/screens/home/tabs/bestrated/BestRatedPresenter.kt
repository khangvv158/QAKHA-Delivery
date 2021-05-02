package com.sun.qakhadelivery.screens.home.tabs.bestrated

import com.sun.qakhadelivery.data.repository.PartnerRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class BestRatedPresenter(
    private val partnerRepository: PartnerRepository
) : BestRatedContract.Presenter {

    private var view: BestRatedContract.View? = null
    private val compositeDisposable = CompositeDisposable()

    override fun getSuggestPartnerBestRated() {
        val disposable = partnerRepository.getSuggestPartnerBestRated()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.getSuggestPartnerBestRatedSuccess(it)
            }, {
                view?.onError(it.localizedMessage)
            })
        compositeDisposable.add(disposable)
    }

    override fun getPartnerById(idPartner: Int) {
        val disposable = partnerRepository.getPartnerById(idPartner)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.getPartnerByIdSuccess(it)
            }, {
                view?.onError(it.localizedMessage)
            })
        compositeDisposable.add(disposable)
    }

    override fun onStart() {
    }

    override fun onStop() {
        compositeDisposable.clear()
    }

    override fun setView(view: BestRatedContract.View?) {
        this.view = view
    }
}
