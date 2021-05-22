package com.sun.qakhadelivery.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sun.qakhadelivery.utils.Constants.DEFAULT_FLOAT
import com.sun.qakhadelivery.utils.Constants.DEFAULT_STRING
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String = DEFAULT_STRING,
    @SerializedName("price") val price: Float = DEFAULT_FLOAT,
    @SerializedName("status") val status: String,
    @SerializedName("description") val description: String = DEFAULT_STRING,
    @SerializedName("category_id") val categoryId: Int,
    @SerializedName("image") val image: Image
) : Parcelable {

    fun inStock() = status == "in_stock"
}
