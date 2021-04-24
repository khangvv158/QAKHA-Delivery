package com.sun.qakhadelivery.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sun.qakhadelivery.utils.Constants.DEFAULT_FLOAT
import com.sun.qakhadelivery.utils.Constants.DEFAULT_STRING
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Order(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: Image,
    @SerializedName("phone_number") val phone_number: String,
    @SerializedName("address") val address: String,
    @SerializedName("latitude") val latitude: Float = 0f,
    @SerializedName("longitude") val longitude: Float = 0f,
    @SerializedName("delivery_time") val delivery_time: String,
    @SerializedName("subtotal") val subtotal: Float,
    @SerializedName("discount") val discount: Float = DEFAULT_FLOAT,
    @SerializedName("shipping_fee") val shipping_fee: Float,
    @SerializedName("total") val total: Float,
    @SerializedName("status") val status: String,
    @SerializedName("type_checkout") val type_checkout: String,
    @SerializedName("description") val description: String = DEFAULT_STRING,
    @SerializedName("driver_id") val driver_id: Int,
    @SerializedName("voucher_id") val voucher_id: Int?,
    @SerializedName("partner_id") val partner_id: Int,
) : Parcelable
