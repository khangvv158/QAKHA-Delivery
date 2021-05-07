package com.sun.qakhadelivery.data.source.remote.schema.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sun.qakhadelivery.data.model.DriverNearest
import com.sun.qakhadelivery.data.model.OrderDetails
import com.sun.qakhadelivery.data.model.UserGPS
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderResponse(
    @SerializedName("order") val order: OrderShippingResponse,
    @SerializedName("order_details") val order_details: MutableList<OrderDetails>,
    @SerializedName("driver_nearest") val driverNearest: DriverNearest,
    @SerializedName("partner") val partner: PartnerShippingResponse,
    @SerializedName("gps_user") val userGPS: UserGPS
) : Parcelable
