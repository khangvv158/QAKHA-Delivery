package com.sun.qakhadelivery.data.repository

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sun.qakhadelivery.utils.Constants

interface DriverFirebaseRepository {
    fun getOnDriverChange(id: Int, callback: ValueEventListener)
}

class DriverFirebaseRepositoryImpl private constructor() : DriverFirebaseRepository {

    private val firebaseReference =
        FirebaseDatabase.getInstance()

    override fun getOnDriverChange(id: Int, callback: ValueEventListener) {
        firebaseReference.reference.child(Constants.DRIVERS)
            .child(Constants.LOCATION)
            .child(Constants.DRIVER)
            .child(id.toString())
            .addValueEventListener(callback)
    }

    companion object {

        private var instance: DriverFirebaseRepositoryImpl? = null
        fun getInstance(): DriverFirebaseRepositoryImpl =
            instance ?: synchronized(this) {
                instance ?: DriverFirebaseRepositoryImpl().also {
                    instance = it
                }
            }
    }
}
