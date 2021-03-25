package com.sun.qakhadelivery.screens.me

import com.sun.qakhadelivery.data.model.TokenAccess
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefs
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefsKey

class MePresenter(private val sharedPrefs: SharedPrefs) : MeContract.Presenter {

    private var view: MeContract.View? = null

    override fun checkSignedIn() {
            val token = sharedPrefs.get(SharedPrefsKey.TOKEN_KEY, TokenAccess::class.java)
            if (token != null) {
                view?.onCheckSignedInSuccess()
            } else {
                view?.onCheckSignedInFailure()
            }
    }

    override fun onStart() = Unit

    override fun onStop() = Unit

    override fun setView(view: MeContract.View?) {
        this.view = view
    }
}
