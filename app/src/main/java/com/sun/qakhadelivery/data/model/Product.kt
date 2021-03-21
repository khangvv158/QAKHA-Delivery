package com.sun.qakhadelivery.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    val id: String,
    val name: String,
    val price: Float,
    val description: String,
    val categoryId: String,
    val partnerId: String,
    val image:Image
) : Parcelable
