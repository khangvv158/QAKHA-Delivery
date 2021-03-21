package com.sun.qakhadelivery.screens.partner.tabs.product

import com.sun.qakhadelivery.data.model.Product
import com.sun.qakhadelivery.utils.BasePresenter

interface ProductPartnerContract {

    interface Presenter : BasePresenter<View>{

        fun getMenus()
    }

    interface View {

        fun onGetMenusSuccess(products: MutableList<Product>)
    }
}
