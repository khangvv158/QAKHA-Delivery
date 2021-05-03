package com.sun.qakhadelivery.screens.profile.update.phone

import com.google.gson.Gson
import com.sun.qakhadelivery.data.repository.TokenRepository
import com.sun.qakhadelivery.data.repository.UserRepository
import com.sun.qakhadelivery.data.source.remote.schema.request.UpdatePhone
import com.sun.qakhadelivery.data.source.remote.schema.response.MessageArrayResponse
import com.sun.qakhadelivery.data.source.remote.schema.response.MessageResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException

class UpdatePhonePresenter(
    private val tokenRepository: TokenRepository,
    private val userRepository: UserRepository
) : UpdatePhoneContract.Presenter {

    private var view: UpdatePhoneContract.View? = null
    private var compositeDisposable = CompositeDisposable()

    override fun updatePhone(updatePhone: UpdatePhone) {
        val disposable = userRepository.updatePhoneNumber(updatePhone, tokenRepository.getToken())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.onSuccessPhone(it)
            }, {
                if (it is HttpException) {
                    try {
                        view?.onErrorPhone(
                            Gson().fromJson(
                                it.response()?.errorBody()?.string(),
                                MessageArrayResponse::class.java
                            ).message.first()
                        )
                    } catch (e: Exception) {
                        view?.onErrorPhone(it.localizedMessage)
                    }
                }
            })
        compositeDisposable.add(disposable)
    }


    override fun onStart() = Unit

    override fun onStop() {
        compositeDisposable.clear()
    }

    override fun setView(view: UpdatePhoneContract.View?) {
        this.view = view
    }
}
