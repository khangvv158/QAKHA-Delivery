package com.sun.qakhadelivery.screens.order.tabs.shipping

import com.sun.qakhadelivery.data.repository.ShippingRepository
import com.sun.qakhadelivery.data.repository.TokenRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ShippingPresenter(
    private val tokenRepository: TokenRepository,
    private val shippingRepository: ShippingRepository
) : ShippingContract.Presenter {

    private var view: ShippingContract.View? = null
    private val compositeDisposable = CompositeDisposable()

    override fun getShipping() {
        val disposable = shippingRepository.getShipping(tokenRepository.getToken())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.onSuccessGetShipping(it)
            }, {
                view?.onErrorGetShipping(it.message.toString())
            })
        compositeDisposable.add(disposable)
    }

    override fun trackingOrder(orderId: Int) {
        val disposable = shippingRepository.trackingOrder(orderId, tokenRepository.getToken())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.onSuccessTrackingOrder(it)
            }, {
                view?.onErrorTrackingOrder(it.message.toString())
            })
        compositeDisposable.add(disposable)
    }

    override fun onStart() = Unit

    override fun onStop() {
        compositeDisposable.clear()
    }

    override fun setView(view: ShippingContract.View?) {
        this.view = view
    }
}
