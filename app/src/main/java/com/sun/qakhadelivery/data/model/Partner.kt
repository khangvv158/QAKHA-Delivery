package com.sun.qakhadelivery.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sun.qakhadelivery.utils.Constants.DEFAULT_FLOAT
import com.sun.qakhadelivery.utils.Constants.DEFAULT_STRING
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Partner(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String = DEFAULT_STRING,
    @SerializedName("address") val address: String = DEFAULT_STRING,
    @SerializedName("image") val image: Image,
    @SerializedName("rate") val rate: Float = DEFAULT_FLOAT,
    @SerializedName("phoneNumber") val phoneNumber: String = DEFAULT_STRING,
    @SerializedName("email") val email: String = DEFAULT_STRING,
    @SerializedName("timeOpen") val timeOpen: String = DEFAULT_STRING,
    @SerializedName("timeClose") val timeClose: String = DEFAULT_STRING,
    @SerializedName("status") val status: String = DEFAULT_STRING,
    @SerializedName("latitude") val latitude: Float?,
    @SerializedName("longitude") val longitude: Float?,
    @SerializedName("categories") val categories: MutableList<Category>
) : Parcelable
