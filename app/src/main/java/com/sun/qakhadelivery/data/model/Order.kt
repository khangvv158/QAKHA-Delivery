package com.sun.qakhadelivery.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sun.qakhadelivery.utils.Constants.DEFAULT_FLOAT
import com.sun.qakhadelivery.utils.Constants.DEFAULT_STRING
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Order(
    @SerializedName("id") val id: Int = -1,
    @SerializedName("name") val name: String = "",
    @SerializedName("phone_number") val phone_number: String = "",
    @SerializedName("address") val address: String = "",
    @SerializedName("delivery_time") val deliveryTime: String,
    @SerializedName("latitude") val latitude: Float = 0f,
    @SerializedName("longitude") val longitude: Float = 0f,
    @SerializedName("subtotal") val subtotal: Float = 0f,
    @SerializedName("discount") val discount: Float = 0f,
    @SerializedName("shipping_fee") val shipping_fee: Float = 0f,
    @SerializedName("total") val total: Float = 0f,
    @SerializedName("status") val status: String = "",
    @SerializedName("type_checkout") val type_checkout: String = "",
    @SerializedName("description") val description: String = "",
    @SerializedName("driver_id") val driver_id: Int = -1,
    @SerializedName("voucher_id") val voucher_id: Int = -1,
    @SerializedName("partner_id") val partner_id: Int = -1,
) : Parcelable
