package com.sun.qakhadelivery.screens.navigate.settingnotsign

import com.sun.qakhadelivery.data.repository.LanguageRepository

class SettingNotSignPresenter(private val languageRepository: LanguageRepository) :
    SettingNotSignContract.Presenter {

    private var view: SettingNotSignContract.View? = null

    override fun getCurrentLanguage() {
        languageRepository.getLanguageCode().let {
            view?.onGetCurrentLanguageSuccess(it)
        }
    }

    override fun setLanguage(langCode: String) {
        languageRepository.setLanguage(langCode).apply {
            view?.onSetLanguageSuccess()
        }
    }

    override fun onStart() {
    }

    override fun onStop() {
        view = null
    }

    override fun setView(view: SettingNotSignContract.View?) {
        this.view = view
    }
}
