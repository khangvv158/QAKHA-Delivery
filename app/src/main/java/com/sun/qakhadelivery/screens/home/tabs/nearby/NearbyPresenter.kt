package com.sun.qakhadelivery.screens.home.tabs.nearby

import com.sun.qakhadelivery.data.repository.PartnerRepository
import com.sun.qakhadelivery.data.source.remote.schema.request.LocationRequest
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class NearbyPresenter(private val partnerRepository: PartnerRepository) : NearbyContract.Presenter {

    private var view: NearbyContract.View? = null
    private val compositeDisposable = CompositeDisposable()

    override fun getSuggestPartnerNearby(locationRequest: LocationRequest) {
        val disposable = partnerRepository.getSuggestPartnerNearby(locationRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.getSuggestPartnerNearbySuccess(it)
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

    override fun setView(view: NearbyContract.View?) {
        this.view = view
    }
}
