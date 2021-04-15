package com.sun.qakhadelivery.screens.checkout

import com.sun.qakhadelivery.data.model.Cart
import com.sun.qakhadelivery.data.model.Product
import com.sun.qakhadelivery.data.repository.CartRepository
import com.sun.qakhadelivery.data.repository.OrderRepository
import com.sun.qakhadelivery.data.repository.TokenRepository
import com.sun.qakhadelivery.data.source.remote.schema.request.ApplyVoucher
import com.sun.qakhadelivery.data.source.remote.schema.request.DistanceRequest
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class CheckoutPresenter(
    private val cartRepository: CartRepository,
    private val orderRepository: OrderRepository,
    private val tokenRepository: TokenRepository
) : CheckoutContract.Presenter {

    private var view: CheckoutContract.View? = null
    private var compositeDisposable = CompositeDisposable()

    override fun getCart(partnerId: Int, products: MutableList<Product>) {
        val disposable = cartRepository.getCart(partnerId, tokenRepository.getToken())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { cartResponses ->
                view?.onUpdateTotalPrice(cartResponses.total)
                cartResponses.products.mapNotNull { response ->
                    products.find { response.productId == it.id }?.let {
                        Cart(it, response.quantity, response.partnerId)
                    }
                }.toMutableList()
            }
            .subscribe({
                view?.onSuccessGetCart(it)
            }, {
                view?.onErrorGetCart(it.message.toString())
            })
        compositeDisposable.add(disposable)
    }

    override fun applyVoucher(applyVoucher: ApplyVoucher) {
        orderRepository.applyVoucher(applyVoucher, tokenRepository.getToken())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.onSuccessApplyVoucher(it)
            }, {
                view?.onErrorApplyVoucher(it.message.toString())
            })
    }

    override fun calculatorPrice(distanceRequest: DistanceRequest) {
    }

    override fun onStart() = Unit

    override fun onStop() {
        compositeDisposable.clear()
    }

    override fun setView(view: CheckoutContract.View?) {
        this.view = view
    }
}
