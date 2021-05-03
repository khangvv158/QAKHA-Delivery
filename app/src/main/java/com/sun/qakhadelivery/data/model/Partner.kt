package com.sun.qakhadelivery.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sun.qakhadelivery.utils.Constants.DEFAULT_FLOAT
import com.sun.qakhadelivery.utils.Constants.DEFAULT_STRING
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Partner(
    @SerializedName("id") var id: Int,
    @SerializedName("name") val name: String = DEFAULT_STRING,
    @SerializedName("address") val address: String = DEFAULT_STRING,
    @SerializedName("image") val image: Image?,
    @SerializedName("phone_number") val phoneNumber: String = DEFAULT_STRING,
    @SerializedName("email") val email: String = DEFAULT_STRING,
    @SerializedName("time_open") val timeOpen: String,
    @SerializedName("time_close") val timeClose: String,
    @SerializedName("status") val status: String = DEFAULT_STRING,
    @SerializedName("latitude") val latitude: Float = DEFAULT_FLOAT,
    @SerializedName("longitude") val longitude: Float = DEFAULT_FLOAT,
    @SerializedName("distance") val distance: Float = DEFAULT_FLOAT,
    @SerializedName("average_point") val avgPoint: Float = DEFAULT_FLOAT,
    @SerializedName("categories") val categories: MutableList<Category>
) : Parcelable {

    fun getProducts(): MutableList<Product> {
        return categories.flatMap { category -> category.products }.toMutableList()
    }
}
