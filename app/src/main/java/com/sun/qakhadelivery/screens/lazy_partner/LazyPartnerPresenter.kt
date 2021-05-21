package com.sun.qakhadelivery.screens.lazy_partner

import com.sun.qakhadelivery.data.repository.PartnerRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class LazyPartnerPresenter(
    private val partnerRepository: PartnerRepository
) : LazyPartnerContract.Presenter {

    private var view: LazyPartnerContract.View? = null
    private val compositeDisposable = CompositeDisposable()

    override fun getPartnerById(id: Int) {
        val disposable = partnerRepository.getPartnerById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.onSuccessGetPartnerById(it)
            }, {
                view?.onErrorGetPartnerById(it.message.toString())
            })
        compositeDisposable.add(disposable)
    }

    override fun onStart() = Unit

    override fun onStop() {
        compositeDisposable.clear()
        view = null
    }

    override fun setView(view: LazyPartnerContract.View?) {
        this.view = view
    }
}
