package com.sun.qakhadelivery.screens.shippingdetail

import com.sun.qakhadelivery.data.model.DriverFirebase
import com.sun.qakhadelivery.utils.BasePresenter

interface ShippingDetailContract {

    interface View {

        fun isOrderShippingByDriverSuccess()
        fun isOrderShippingByDriverDone()
        fun fetchLocationByDriverSuccess(driverFirebase: DriverFirebase)
        fun onError(message: String)
    }

    interface Presenter : BasePresenter<View> {

        fun isOrderShippingByDriver(idOrder: Int, idDriver: Int)
        fun fetchLocationByDriver(idDriver: Int)
    }
}
