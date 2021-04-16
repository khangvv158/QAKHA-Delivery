package com.sun.qakhadelivery.screens.address

import com.sun.qakhadelivery.data.model.Address
import com.sun.qakhadelivery.utils.BasePresenter

interface AddressContract {

    interface View {

        fun onGetAddressesSuccess(addresses: MutableList<Address>)
        fun onDeleteAddressSuccess()
        fun onError(message: String)
    }

    interface Presenter : BasePresenter<View> {

        fun getAddresses()
        fun deleteAddress(idAddress: Int)
    }
}
