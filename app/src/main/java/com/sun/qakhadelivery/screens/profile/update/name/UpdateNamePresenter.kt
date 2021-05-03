package com.sun.qakhadelivery.screens.profile.update.name

import com.google.gson.Gson
import com.sun.qakhadelivery.data.repository.TokenRepository
import com.sun.qakhadelivery.data.repository.UserRepository
import com.sun.qakhadelivery.data.source.remote.schema.request.UpdateUsername
import com.sun.qakhadelivery.data.source.remote.schema.response.MessageArrayResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException

class UpdateNamePresenter(
    private val tokenRepository: TokenRepository,
    private val userRepository: UserRepository
) : UpdateNameContract.Presenter {

    private var view: UpdateNameContract.View? = null
    private var compositeDisposable = CompositeDisposable()

    override fun updateUsername(updateUsername: UpdateUsername) {
        val disposable = userRepository.updateUsername(updateUsername, tokenRepository.getToken())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.onSuccessUsername(it)
            }, {
                if (it is HttpException) {
                    try {
                        view?.onErrorUsername(
                            Gson().fromJson(
                                it.response()?.errorBody()?.string(),
                                MessageArrayResponse::class.java
                            ).message.first()
                        )
                    } catch (e: Exception) {
                        view?.onErrorUsername(it.localizedMessage)
                    }
                }
            })
        compositeDisposable.add(disposable)
    }

    override fun onStart() = Unit

    override fun onStop() {
        compositeDisposable.clear()
    }

    override fun setView(view: UpdateNameContract.View?) {
        this.view = view
    }
}
