package com.sun.qakhadelivery.screens.partner

import com.sun.qakhadelivery.data.model.Cart
import com.sun.qakhadelivery.data.model.Product
import com.sun.qakhadelivery.data.repository.CartRepository
import com.sun.qakhadelivery.data.source.remote.schema.request.CartRequest
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class PartnerPresenter(private val cartRepository: CartRepository) : PartnerContract.Presenter {

    private var view: PartnerContract.View? = null
    private var compositeDisposable = CompositeDisposable()

    override fun getCart(partnerId: Int, products: MutableList<Product>) {
        val disposable = cartRepository.getCart(partnerId).map { cartResponses ->
            cartResponses.products.mapNotNull { response ->
                products.find { response.productId == it.id }?.let {
                    Cart(it, response.quantity, response.partnerId)
                }
            }.toMutableList()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.onSuccessGetCart(it)
            }, {
                view?.onErrorGetCart(it.message.toString())
            })
        compositeDisposable.add(disposable)
    }

    override fun createCart(cartRequest: CartRequest, products: MutableList<Product>) {
        val disposable = cartRepository.createCart(cartRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { cartResponses ->
                cartResponses.products.mapNotNull { response ->
                    products.find { response.productId == it.id }?.let { products ->
                        Cart(products, response.quantity, response.partnerId)
                    }
                }.toMutableList()
            }
            .subscribe({
                view?.onSuccessCreateCart(it)
            }, {
                view?.onErrorCreateCart(it.message.toString())
            })
        compositeDisposable.add(disposable)
    }

    override fun updateCart(cartRequest: CartRequest, products: MutableList<Product>) {
        val disposable = cartRepository.updateCart(cartRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { cartResponses ->
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

    override fun onStart() = Unit

    override fun onStop() {
        compositeDisposable.clear()
    }

    override fun setView(view: PartnerContract.View?) {
        this.view = view
    }
}
