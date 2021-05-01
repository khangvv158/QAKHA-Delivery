package com.sun.qakhadelivery.screens.orderdetail

import com.sun.qakhadelivery.data.repository.FeedbackRepositoryImpl
import com.sun.qakhadelivery.data.repository.HistoryRepository
import com.sun.qakhadelivery.data.repository.TokenRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class OrderDetailPresenter(
    private val historyRepository: HistoryRepository,
    private val tokenRepository: TokenRepository,
    private val feedbackRepository: FeedbackRepositoryImpl
) : OrderDetailContract.Presenter {

    private val compositeDisposable = CompositeDisposable()
    private var view: OrderDetailContract.View? = null

    override fun getOrderDetail(orderId: Int) {
        val disposable = historyRepository.getOrderDetails(orderId, tokenRepository.getToken())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.onSuccessOrderDetails(it)
            }, {
                view?.onErrorOrderDetails(it.message.toString())
            })
        compositeDisposable.add(disposable)
    }

    override fun checkDriverFeedback(orderId: Int) {
        val disposable = feedbackRepository.checkFeedBack(orderId, tokenRepository.getToken())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.onSuccessCheckDriverFeedback(it)
            }, {
                view?.onErrorCheckDriverFeedback(it.message.toString())
            })
        compositeDisposable.add(disposable)
    }

    override fun onStart() = Unit

    override fun onStop() {
        compositeDisposable.clear()
    }

    override fun setView(view: OrderDetailContract.View?) {
        this.view = view
    }
}
