package com.sun.qakhadelivery.screens.feedback.partner

import com.sun.qakhadelivery.data.repository.FeedbackRepository
import com.sun.qakhadelivery.data.repository.TokenRepository
import com.sun.qakhadelivery.data.source.remote.schema.request.RatePartnerRequest
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class PartnerFeedbackPresenter(
    private val feedbackRepository: FeedbackRepository,
    private val tokenRepository: TokenRepository
) : PartnerFeedbackContact.Presenter {

    private var view: PartnerFeedbackContact.View? = null
    private var compositeDisposable = CompositeDisposable()

    override fun feedbackPartner(ratePartnerRequest: RatePartnerRequest) {
        val disposable = feedbackRepository.ratePartner(
            ratePartnerRequest,
            tokenRepository.getToken()
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.onSuccessFeedbackPartner(it)
            }, {
                view?.onErrorFeedbackPartner(it.localizedMessage)
            })
        compositeDisposable.add(disposable)
    }

    override fun onStart() = Unit

    override fun onStop() {
        compositeDisposable.clear()
    }

    override fun setView(view: PartnerFeedbackContact.View?) {
        this.view = view
    }
}
