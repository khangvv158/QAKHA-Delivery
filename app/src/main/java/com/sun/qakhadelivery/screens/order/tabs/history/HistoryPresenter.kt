package com.sun.qakhadelivery.screens.order.tabs.history

import com.sun.qakhadelivery.data.repository.HistoryRepository
import com.sun.qakhadelivery.data.repository.TokenRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class HistoryPresenter(
    private val tokenRepository: TokenRepository,
    private val historyRepository: HistoryRepository
) : HistoryContract.Presenter {

    private var view: HistoryContract.View? = null
    private val compositeDisposable = CompositeDisposable()

    override fun getHistory() {
        val disposable = historyRepository.getHistory(tokenRepository.getToken())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.onSuccessGetHistory(it)
            }, {
                view?.onErrorGetHistory(it.message.toString())
            })
        compositeDisposable.add(disposable)
    }

    override fun onStart() = Unit

    override fun onStop() {
        compositeDisposable.clear()
    }

    override fun setView(view: HistoryContract.View?) {
        this.view = view
    }
}
