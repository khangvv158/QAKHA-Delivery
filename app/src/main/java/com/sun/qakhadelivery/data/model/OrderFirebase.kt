package com.sun.qakhadelivery.data.model

import com.google.firebase.database.PropertyName

data class OrderFirebase(
    @set :PropertyName("driver_id")
    @get :PropertyName("driver_id")
    var idDriver: Int = -1,
    @set :PropertyName("order_id")
    @get :PropertyName("order_id")
    var idOrder: Int = -1
)
