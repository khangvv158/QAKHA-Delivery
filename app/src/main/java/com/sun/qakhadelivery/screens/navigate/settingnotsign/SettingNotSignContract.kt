package com.sun.qakhadelivery.screens.navigate.settingnotsign

import com.sun.qakhadelivery.utils.BasePresenter

interface SettingNotSignContract {

    interface View {

        fun onGetCurrentLanguageSuccess(langCode: String)
        fun onSetLanguageSuccess()
        fun onError(message: String)
    }

    interface Presenter : BasePresenter<View> {

        fun getCurrentLanguage()
        fun setLanguage(langCode: String)
    }
}
