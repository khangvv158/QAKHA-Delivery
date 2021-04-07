package com.sun.qakhadelivery.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sun.qakhadelivery.utils.Constants.DEFAULT_STRING
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String = DEFAULT_STRING,
    @SerializedName("parent_id") val parentId: Int,
    @SerializedName("partner_id") val partnerId: Int,
    @SerializedName("products") val products: MutableList<Product>
) : Parcelable
