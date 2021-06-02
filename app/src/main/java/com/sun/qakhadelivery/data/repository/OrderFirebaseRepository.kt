package com.sun.qakhadelivery.data.repository

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sun.qakhadelivery.utils.Constants

interface OrderFirebaseRepository {

    fun listenerOrder(idDriver: Int, callback: ValueEventListener)
}

class OrderFirebaseRepositoryImpl private constructor() : OrderFirebaseRepository {

    private val firebaseClient = FirebaseDatabase.getInstance()

    override fun listenerOrder(idDriver: Int, callback: ValueEventListener) {
        firebaseClient.reference.child(Constants.DRIVERS)
            .child(Constants.SHIPPING)
            .child(Constants.ORDER)
            .child(idDriver.toString())
            .addValueEventListener(callback)
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
