package com.sun.qakhadelivery.data.source.remote.schema.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sun.qakhadelivery.data.model.*
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderResponse(
    @SerializedName("order") val order: Order,
    @SerializedName("order_details") val order_details: MutableList<OrderDetails>,
    @SerializedName("driver_nearest") val driverNearest: DriverNearest,
    @SerializedName("partner") val partner: Partner,
    @SerializedName("gps_user") val userGPS: UserGPS
) : Parcelable
