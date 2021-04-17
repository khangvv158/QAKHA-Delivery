package com.sun.qakhadelivery.screens.partner.tabs.review

import com.sun.qakhadelivery.data.repository.PartnerRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ReviewPresenter(private val partnerRepository: PartnerRepository) : ReviewContract.Presenter {

    private var view: ReviewContract.View? = null
    private var compositeDisposable = CompositeDisposable()

    override fun getFeedback(idPartner: Int) {
        val disposable = partnerRepository.getFeedbackByIdPartner(idPartner)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.onGetFeedbackSuccess(it)
            }, {
                view?.onError(it.localizedMessage)
            })
        compositeDisposable.add(disposable)
    }

    override fun onStart() = Unit

    override fun onStop() {
        compositeDisposable.clear()
    }

    override fun setView(view: ReviewContract.View?) {
        this.view = view
    }
}
