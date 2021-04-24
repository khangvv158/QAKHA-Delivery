package com.sun.qakhadelivery.data.repository

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.FirebaseDatabase
import com.sun.qakhadelivery.utils.Constants

interface OrderFirebaseRepository {

    fun listenerOrder(idDriver: Int, callback: ChildEventListener)
}

class OrderFirebaseRepositoryImpl private constructor() : OrderFirebaseRepository {

    private val firebaseClient = FirebaseDatabase.getInstance()

    override fun listenerOrder(idDriver: Int, callback: ChildEventListener) {
        firebaseClient.reference.child(Constants.DRIVERS)
            .child(Constants.SHIPPING)
            .child(idDriver.toString())
            .child(Constants.ORDER)
            .addChildEventListener(callback)
    }

    companion object {
        private var instance: OrderFirebaseRepositoryImpl? = null

        fun getInstance(): OrderFirebaseRepositoryImpl =
            instance ?: synchronized(this) {
                instance ?: OrderFirebaseRepositoryImpl().also {
                    instance = it
                }
            }
    }
}
