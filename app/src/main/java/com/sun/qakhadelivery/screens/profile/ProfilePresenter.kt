package com.sun.qakhadelivery.screens.profile

import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.google.gson.Gson
import com.sun.qakhadelivery.data.repository.CloudRepository
import com.sun.qakhadelivery.data.repository.TokenRepository
import com.sun.qakhadelivery.data.repository.UserRepository
import com.sun.qakhadelivery.data.source.remote.schema.request.UpdateImage
import com.sun.qakhadelivery.data.source.remote.schema.response.MessageArrayResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException

class ProfilePresenter(
    private val tokenRepository: TokenRepository,
    private val userRepository: UserRepository,
    private val cloudRepository: CloudRepository
) : ProfileContract.Presenter {

    private var view: ProfileContract.View? = null
    private var compositeDisposable = CompositeDisposable()

    override fun getUser() {
        val disposable = userRepository.getUser(tokenRepository.getToken())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.onSuccessUser(it)
            }, {
                view?.onErrorUser(it.localizedMessage)
            })
        compositeDisposable.add(disposable)
    }

    override fun uploadImage(file: String) {
        cloudRepository.uploadFileToCloud(file, object : UploadCallback {

            override fun onStart(requestId: String?) = Unit

            override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) = Unit

            override fun onSuccess(requestId: String?, resultData: MutableMap<Any?, Any?>?) {
                view?.onSuccessUploadImage(resultData?.get("url").toString())
            }

            override fun onError(requestId: String?, error: ErrorInfo?) {
                error?.let { view?.onErrorUploadImage(it.description) }
            }

            override fun onReschedule(requestId: String?, error: ErrorInfo?) {
                error?.let { view?.onErrorUploadImage(it.description) }
            }
        })
    }

    override fun updateImage(updateImage: UpdateImage) {
        val disposable = userRepository.updateImage(updateImage, tokenRepository.getToken())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.onSuccessUpdateImage(it)
            }, {
                if (it is HttpException) {
                    try {
                        view?.onErrorUpdateImage(
                            Gson().fromJson(
                                it.response()?.errorBody()?.string(),
                                MessageArrayResponse::class.java
                            ).message.first()
                        )
                    } catch (e: Exception) {
                        view?.onErrorUpdateImage(it.localizedMessage)
                    }
                }
            })
        compositeDisposable.add(disposable)
    }

    override fun onStart() = Unit

    override fun onStop() {
        compositeDisposable.clear()
    }

    override fun setView(view: ProfileContract.View?) {
        this.view = view
    }
}
