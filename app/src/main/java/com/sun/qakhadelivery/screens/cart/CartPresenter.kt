package com.sun.qakhadelivery.screens.cart

import com.sun.qakhadelivery.data.model.Cart
import com.sun.qakhadelivery.data.model.Product
import com.sun.qakhadelivery.data.repository.CartRepository
import com.sun.qakhadelivery.data.repository.TokenRepository
import com.sun.qakhadelivery.data.source.remote.schema.request.CartRequest
import com.sun.qakhadelivery.data.source.remote.schema.request.RemoveCartRequest
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class CartPresenter(
    private val cartRepository: CartRepository,
    private val tokenRepository: TokenRepository
) : CartContract.Presenter {

    private var view: CartContract.View? = null
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

    override fun createCart(cartRequest: CartRequest, products: MutableList<Product>) {
        val disposable = cartRepository.createCart(cartRequest, tokenRepository.getToken())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { cartResponses ->
                view?.onUpdateTotalPrice(cartResponses.total)
                cartResponses.products.mapNotNull { response ->
                    products.find { response.productId == it.id }?.let { products ->
                        Cart(products, response.quantity, response.partnerId)
                    }
                }.toMutableList()
            }
            .subscribe({
                view?.onSuccessUpdateCart(it)
            }, {
                view?.onErrorUpdateCart(it.message.toString())
            })
        compositeDisposable.add(disposable)
    }

    override fun updateCart(cartRequest: CartRequest, products: MutableList<Product>) {
        val disposable = cartRepository.updateCart(cartRequest, tokenRepository.getToken())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { cartResponses ->
                view?.onUpdateTotalPrice(cartResponses.total)
                cartResponses.products.mapNotNull { response ->
                    products.find { response.productId == it.id }?.let { products ->
                        Cart(products, response.quantity, response.partnerId)
                    }
                }.toMutableList()
            }
            .subscribe({
                view?.onSuccessUpdateCart(it)
            }, {
                view?.onErrorUpdateCart(it.message.toString())
            })
        compositeDisposable.add(disposable)
    }

    override fun removeCart(removeCartRequest: RemoveCartRequest, products: MutableList<Product>) {
        val disposable = cartRepository.removeCart(removeCartRequest, tokenRepository.getToken())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { cartResponses ->
                view?.onUpdateTotalPrice(cartResponses.total)
                cartResponses.products.mapNotNull { response ->
                    products.find { response.productId == it.id }?.let { products ->
                        Cart(products, response.quantity, response.partnerId)
                    }
                }.toMutableList()
            }
            .subscribe({
                view?.onSuccessRemoveCart(it)
            }, {
                view?.onErrorRemoveCart(it.message.toString())
            })
        compositeDisposable.add(disposable)
    }

    override fun clearCart(partnerId: Int) {
        val disposable = cartRepository.clearCart(partnerId, tokenRepository.getToken())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.onSuccessClearCart()
            }, {
                view?.onErrorClearCart(it.message.toString())
            })
        compositeDisposable.add(disposable)
    }

    override fun onStart() = Unit

    override fun onStop() {
        compositeDisposable.clear()
    }

    override fun setView(view: CartContract.View?) {
        this.view = view
    }
}
