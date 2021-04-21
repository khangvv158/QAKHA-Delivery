package com.sun.qakhadelivery.screens.checkout

import com.sun.qakhadelivery.data.model.Cart
import com.sun.qakhadelivery.data.model.Product
import com.sun.qakhadelivery.data.repository.CartRepository
import com.sun.qakhadelivery.data.repository.OrderRepository
import com.sun.qakhadelivery.data.repository.TokenRepository
import com.sun.qakhadelivery.data.repository.UserRepositoryImpl
import com.sun.qakhadelivery.data.source.remote.schema.request.ApplyVoucher
import com.sun.qakhadelivery.data.source.remote.schema.request.DistanceRequest
import com.sun.qakhadelivery.data.source.remote.schema.request.OrderRequest
import com.sun.qakhadelivery.data.source.remote.schema.request.VoucherCancel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class CheckoutPresenter(
    private val cartRepository: CartRepository,
    private val orderRepository: OrderRepository,
    private val tokenRepository: TokenRepository,
    private val userRepository: UserRepositoryImpl
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
        val disposable = orderRepository.applyVoucher(applyVoucher, tokenRepository.getToken())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.onSuccessApplyVoucher(it)
            }, {
                view?.onErrorApplyVoucher(it.message.toString())
            })
        compositeDisposable.add(disposable)
    }

    override fun cancelVoucher(voucherCancel: VoucherCancel) {
        val disposable = orderRepository.cancelVoucher(voucherCancel, tokenRepository.getToken())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.onSuccessCancelVouchers(it)
            }, {
                view?.onErrorCancelVouchers(it.message.toString())
            })
        compositeDisposable.add(disposable)
    }

    override fun getVouchers(partnerId: Int) {
        val disposable = orderRepository.showVoucher(partnerId, tokenRepository.getToken())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.onSuccessGetVouchers(it)
            }, {
                view?.onErrorGetVouchers(it.message.toString())
            })
        compositeDisposable.add(disposable)
    }

    override fun getUserInfo() {
        val disposable = userRepository.getUser(tokenRepository.getToken())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.onSuccessGetUser(it)
            }, {
                view?.onErrorGetVouchers(it.message.toString())
            })
        compositeDisposable.add(disposable)
    }

    override fun calculatorDistance(distanceRequest: DistanceRequest) {
        val disposable = orderRepository.calculateDistance(
            distanceRequest,
            tokenRepository.getToken()
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.onSuccessDistance(it)
            }, {
                view?.onErrorCalculatorDistance(it.message.toString())
            })
        compositeDisposable.add(disposable)
    }

    override fun createOrder(orderRequest: OrderRequest) {
        val disposable = orderRepository.createOrder(
            orderRequest,
            tokenRepository.getToken()
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.onSuccessCreateOrder(it)
            }, {
                view?.onErrorCreateOrder(it.message.toString())
            })
        compositeDisposable.add(disposable)
    }


    override fun onStart() = Unit

    override fun onStop() {
        compositeDisposable.clear()
    }

    override fun setView(view: CheckoutContract.View?) {
        this.view = view
    }
}
