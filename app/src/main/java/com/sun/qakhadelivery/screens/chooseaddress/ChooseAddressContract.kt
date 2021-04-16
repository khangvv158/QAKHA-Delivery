package com.sun.qakhadelivery.screens.chooseaddress

import com.sun.qakhadelivery.utils.BasePresenter

interface ChooseAddressContract {

    interface View {

        fun onCreateAddressSuccess()
        fun onUpdateAddressSuccess()
        fun onError(message: String)
    }

    interface Presenter : BasePresenter<View> {

        fun createAddress(name: String, latitude: Double, longitude: Double)
        fun updateAddress(name: String, latitude: Double, longitude: Double)
    }
}
