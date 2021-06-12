package com.sun.qakhadelivery.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sun.qakhadelivery.data.model.ProductStatus.DISABLE
import com.sun.qakhadelivery.data.model.ProductStatus.IN_STOCK
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

    fun inStock() = status == IN_STOCK

    fun productNotAvailable() = status == DISABLE
}

object ProductStatus {
    const val IN_STOCK = "in_stock"
    const val OUT_OF_STOCK = "out_of_stock"
    const val DISABLE = "disabled"
}
