package com.sun.qakhadelivery.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cart(
    val product: Product,
    var amount: Int = 0
) : Parcelable
