package com.sun.qakhadelivery.screens.main

import com.sun.qakhadelivery.utils.BasePresenter

interface MainContract {

    interface View {

        fun onSetupLanguageSuccess(langCode: String)
    }

    interface Presenter : BasePresenter<View> {

        fun onSetupLanguage()
    }
}
