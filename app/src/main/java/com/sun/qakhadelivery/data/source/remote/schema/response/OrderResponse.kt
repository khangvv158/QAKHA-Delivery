package com.sun.qakhadelivery.data.source.remote.schema.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sun.qakhadelivery.data.model.DriverNearest
import com.sun.qakhadelivery.data.model.Order
import com.sun.qakhadelivery.data.model.OrderDetails
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderResponse(
    @SerializedName("order") val order: Order,
    @SerializedName("order_details") val order_details: MutableList<OrderDetails>,
    @SerializedName("driver_nearest") val driverNearest: DriverNearest,
) : Parcelable
