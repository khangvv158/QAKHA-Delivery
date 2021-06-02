package com.sun.qakhadelivery.screens.shippingdetail

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.sun.qakhadelivery.data.model.DriverFirebase
import com.sun.qakhadelivery.data.model.OrderFirebase
import com.sun.qakhadelivery.data.repository.DriverFirebaseRepository
import com.sun.qakhadelivery.data.repository.OrderFirebaseRepository

class ShippingDetailPresenter(
    private val orderFirebaseRepository: OrderFirebaseRepository,
    private val driverFirebaseRepository: DriverFirebaseRepository
) :
    ShippingDetailContract.Presenter {

    private var view: ShippingDetailContract.View? = null

    override fun isOrderShippingByDriver(idOrder: Int, idDriver: Int) {
        orderFirebaseRepository.listenerOrder(idDriver, object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.getValue(OrderFirebase::class.java).let {
                    if (idOrder == it?.idOrder) {
                        view?.isOrderShippingByDriverSuccess()
                    }
                    if (it == null) {
                        view?.isOrderShippingByDriverDone()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                view?.onError(error.message)
            }
        })
    }

    override fun fetchLocationByDriver(idDriver: Int) {
        driverFirebaseRepository.getOnDriverChange(idDriver, object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.getValue(DriverFirebase::class.java)
                data?.let { view?.fetchLocationByDriverSuccess(it) }
            }

            override fun onCancelled(error: DatabaseError) {
                view?.onError(error.message)
            }
        })
    }

    override fun onStart() {
    }

    override fun onStop() {
        view = null
    }

    override fun setView(view: ShippingDetailContract.View?) {
        this.view = view
    }
}
