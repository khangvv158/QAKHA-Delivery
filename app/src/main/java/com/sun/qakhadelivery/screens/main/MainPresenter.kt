package com.sun.qakhadelivery.screens.main

import com.sun.qakhadelivery.data.repository.LanguageRepository

class MainPresenter(private var languageRepository: LanguageRepository) : MainContract.Presenter {

    private var view: MainContract.View? = null

    override fun onSetupLanguage() {
        languageRepository.getLanguageCode().let {
            view?.onSetupLanguageSuccess(it)
        }
    }

    override fun onStart() {
    }

    override fun onStop() {
    }

    override fun setView(view: MainContract.View?) {
        this.view = view
    }
}
