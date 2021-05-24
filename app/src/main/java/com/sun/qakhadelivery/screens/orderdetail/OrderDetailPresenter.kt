package com.sun.qakhadelivery.screens.orderdetail

import io.reactivex.rxjava3.disposables.CompositeDisposable

class OrderDetailPresenter : OrderDetailContract.Presenter {

    private val compositeDisposable = CompositeDisposable()
    private var view: OrderDetailContract.View? = null

    override fun onStart() = Unit

    override fun onStop() {
        compositeDisposable.clear()
    }

    override fun setView(view: OrderDetailContract.View?) {
        this.view = view
    }
}
