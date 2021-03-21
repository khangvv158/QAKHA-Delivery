package com.sun.qakhadelivery.screens.partner.tabs.product

import com.sun.qakhadelivery.utils.items

class ProductPartnerPresenter : ProductPartnerContract.Presenter {

    private var view: ProductPartnerContract.View? = null

    override fun getMenus() {
        items().also {
            view?.onGetMenusSuccess(it)
        }
    }

    override fun onStart() = Unit

    override fun onStop() = Unit

    override fun setView(view: ProductPartnerContract.View?) {
        this.view = view
    }
}
