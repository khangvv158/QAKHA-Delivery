package com.sun.qakhadelivery.screens.lazy_order_details

import com.sun.qakhadelivery.data.model.OrderDetailMerge
import com.sun.qakhadelivery.data.repository.FeedbackRepositoryImpl
import com.sun.qakhadelivery.data.repository.HistoryRepository
import com.sun.qakhadelivery.data.repository.TokenRepository
import com.sun.qakhadelivery.data.source.remote.schema.response.HistoryResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class LazyOrderDetailsPresenter(
    private val historyRepository: HistoryRepository,
    private val tokenRepository: TokenRepository,
    private val feedbackRepository: FeedbackRepositoryImpl
) : LazyOrderDetailsContract.Presenter {

    private var view: LazyOrderDetailsContract.View? = null
    private val compositeDisposable = CompositeDisposable()

    override fun getOrderDetail(orderId: Int, historyResponse: HistoryResponse) {
        val disposable = Observable.zip(
            historyRepository.getOrderDetails(orderId, tokenRepository.getToken()),
            feedbackRepository.checkFeedBack(orderId, tokenRepository.getToken()),
            { orderDetails, feedback ->
                OrderDetailMerge(orderDetails, feedback, historyResponse)
            }
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.onSuccessOrderDetails(it)
            }, {
                view?.onErrorOrderDetails(it.localizedMessage)
            })
        compositeDisposable.add(disposable)
    }

    override fun onStart() = Unit

    override fun onStop() {
        compositeDisposable.clear()
        view = null
    }

    override fun setView(view: LazyOrderDetailsContract.View?) {
        this.view = view
    }
}
