package com.sun.qakhadelivery.screens.navigate.setting

import com.sun.qakhadelivery.utils.BasePresenter

interface SettingContract {

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
