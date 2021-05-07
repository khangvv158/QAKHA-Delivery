package com.sun.qakhadelivery.screens.checkout

import com.google.gson.Gson
import com.sun.qakhadelivery.data.model.Cart
import com.sun.qakhadelivery.data.model.Product
import com.sun.qakhadelivery.data.repository.CartRepository
import com.sun.qakhadelivery.data.repository.OrderRepository
import com.sun.qakhadelivery.data.repository.TokenRepository
import com.sun.qakhadelivery.data.repository.UserRepositoryImpl
import com.sun.qakhadelivery.data.source.remote.schema.request.*
import com.sun.qakhadelivery.data.source.remote.schema.response.MessageResponse
import com.sun.qakhadelivery.data.source.remote.schema.response.OrderVoucherRequest
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException

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
                if (it is HttpException) {
                    try {
                        view?.onErrorGetCart(
                            Gson().fromJson(
                                it.response()?.errorBody()?.string(),
                                MessageResponse::class.java
                            ).message
                        )
                    } catch (e: Exception) {
                        view?.onErrorGetCart(it.localizedMessage)
                    }
                }
            })
        compositeDisposable.add(disposable)
    }

    override fun applyVoucherTotal(voucherTotal: VoucherTotal) {
        val disposable = orderRepository.applyVoucherTotal(voucherTotal, tokenRepository.getToken())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.onSuccessApplyVoucher(it)
            }, {
                if (it is HttpException) {
                    try {
                        view?.onErrorApplyVoucher(
                            Gson().fromJson(
                                it.response()?.errorBody()?.string(),
                                MessageResponse::class.java
                            ).message
                        )
                    } catch (e: Exception) {
                        view?.onErrorApplyVoucher(it.localizedMessage)
                    }
                }
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
                if (it is HttpException) {
                    try {
                        view?.onErrorCancelVouchers(
                            Gson().fromJson(
                                it.response()?.errorBody()?.string(),
                                MessageResponse::class.java
                            ).message
                        )
                    } catch (e: Exception) {
                        view?.onErrorCancelVouchers(it.localizedMessage)
                    }
                }
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
                if (it is HttpException) {
                    try {
                        view?.onErrorGetVouchers(
                            Gson().fromJson(
                                it.response()?.errorBody()?.string(),
                                MessageResponse::class.java
                            ).message
                        )
                    } catch (e: Exception) {
                        view?.onErrorGetVouchers(it.localizedMessage)
                    }
                }
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
                if (it is HttpException) {
                    try {
                        view?.onErrorGetUser(
                            Gson().fromJson(
                                it.response()?.errorBody()?.string(),
                                MessageResponse::class.java
                            ).message
                        )
                    } catch (e: Exception) {
                        view?.onErrorGetUser(it.localizedMessage)
                    }
                }
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
                if (it is HttpException) {
                    try {
                        view?.onErrorCalculatorDistance(
                            Gson().fromJson(
                                it.response()?.errorBody()?.string(),
                                MessageResponse::class.java
                            ).message
                        )
                    } catch (e: Exception) {
                        view?.onErrorCalculatorDistance(it.localizedMessage)
                    }
                }
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
                if (it is HttpException) {
                    try {
                        view?.onErrorCreateOrder(
                            Gson().fromJson(
                                it.response()?.errorBody()?.string(),
                                MessageResponse::class.java
                            ).message
                        )
                    } catch (e: Exception) {
                        view?.onErrorCreateOrder(it.localizedMessage)
                    }
                }
            })
        compositeDisposable.add(disposable)
    }

    override fun createOrderWithVoucher(orderVoucherRequest: OrderVoucherRequest) {
        val disposable = orderRepository.createOrderWithVoucher(
            orderVoucherRequest,
            tokenRepository.getToken()
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.onSuccessCreateOrder(it)
            }, {
                if (it is HttpException) {
                    try {
                        view?.onErrorCreateOrder(
                            Gson().fromJson(
                                it.response()?.errorBody()?.string(),
                                MessageResponse::class.java
                            ).message
                        )
                    } catch (e: Exception) {
                        view?.onErrorCreateOrder(it.localizedMessage)
                    }
                }
            })
        compositeDisposable.add(disposable)
    }

    override fun applyVoucherDistance(voucherDistance: VoucherDistance) {
        val disposable =
            orderRepository.applyVoucherDistance(voucherDistance, tokenRepository.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.onSuccessApplyVoucher(it)
                }, {
                    if (it is HttpException) {
                        try {
                            view?.onErrorApplyVoucher(
                                Gson().fromJson(
                                    it.response()?.errorBody()?.string(),
                                    MessageResponse::class.java
                                ).message
                            )
                        } catch (e: Exception) {
                            view?.onErrorApplyVoucher(it.localizedMessage)
                        }
                    }
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
