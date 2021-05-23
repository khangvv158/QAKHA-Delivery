package com.sun.qakhadelivery.screens.address

import com.sun.qakhadelivery.data.repository.AddressRepository
import com.sun.qakhadelivery.data.repository.TokenRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class AddressPresenter(
    private val addressRepository: AddressRepository,
    private val tokenRepository: TokenRepository
) : AddressContract.Presenter {

    private var view: AddressContract.View? = null
    private val compositeDisposable = CompositeDisposable()

    override fun getAddresses() {
        val disposable = addressRepository.getAddresses(tokenRepository.getToken())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.onGetAddressesSuccess(it)
            }, {
                view?.onError(it.localizedMessage)
            })
        compositeDisposable.add(disposable)
    }

    override fun deleteAddress(idAddress: Int) {
        val disposable = addressRepository.deleteAddress(idAddress, tokenRepository.getToken())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.onDeleteAddressSuccess()
            }, {
                view?.onError(it.localizedMessage)
            })
        compositeDisposable.add(disposable)
    }

    override fun onStart() = Unit

    override fun onStop() {
        compositeDisposable.clear()
        view = null
    }

    override fun setView(view: AddressContract.View?) {
        this.view = view
    }
}
