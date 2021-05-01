package com.sun.qakhadelivery.screens.feedback.driver

import com.sun.qakhadelivery.data.repository.FeedbackRepository
import com.sun.qakhadelivery.data.repository.TokenRepository
import com.sun.qakhadelivery.data.source.remote.schema.request.RateDriverRequest
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class DriverFeedbackPresenter(
    private val feedbackRepository: FeedbackRepository,
    private val tokenRepository: TokenRepository
) : DriverFeedbackContact.Presenter {

    private var view: DriverFeedbackContact.View? = null
    private var compositeDisposable = CompositeDisposable()

    override fun feedbackDriver(rateDriverRequest: RateDriverRequest) {
        val disposable = feedbackRepository.rateDriver(
            rateDriverRequest,
            tokenRepository.getToken()
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.onSuccessFeedback(it)
            }, {
                view?.onErrorFeedback(it.localizedMessage)
            })
        compositeDisposable.add(disposable)
    }

    override fun onStart() = Unit

    override fun onStop() {
        compositeDisposable.clear()
    }

    override fun setView(view: DriverFeedbackContact.View?) {
        this.view = view
    }
}
