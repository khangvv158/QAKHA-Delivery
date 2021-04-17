package com.sun.qakhadelivery.screens.chooseaddress

import com.sun.qakhadelivery.data.repository.AddressRepository
import com.sun.qakhadelivery.data.repository.TokenRepository
import com.sun.qakhadelivery.data.source.remote.schema.request.AddressRequest
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ChooseAddressPresenter(
    private val addressRepository: AddressRepository,
    private val tokenRepository: TokenRepository
) : ChooseAddressContract.Presenter {

    private var view: ChooseAddressContract.View? = null
    private val compositeDisposable = CompositeDisposable()

    override fun createAddress(name: String, latitude: Double, longitude: Double) {
        val disposable = addressRepository.createAddress(
            AddressRequest(name, latitude.toFloat(), longitude.toFloat()),
            tokenRepository.getToken()
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.onCreateAddressSuccess()
            }, {
                view?.onError(it.localizedMessage)
            })
        compositeDisposable.add(disposable)
    }

    override fun updateAddress(name: String, latitude: Double, longitude: Double, idAddress: Int) {
        val disposable = addressRepository.updateAddress(
            AddressRequest(name, latitude.toFloat(), longitude.toFloat()), idAddress,
            tokenRepository.getToken()
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.onCreateAddressSuccess()
            }, {
                view?.onError(it.localizedMessage)
            })
        compositeDisposable.add(disposable)
    }

    override fun onStart() = Unit

    override fun onStop() {
        compositeDisposable.clear()
    }

    override fun setView(view: ChooseAddressContract.View?) {
        this.view = view
    }
}
