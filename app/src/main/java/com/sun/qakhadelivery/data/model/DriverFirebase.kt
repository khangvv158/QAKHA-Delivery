package com.sun.qakhadelivery.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DriverFirebase(
    val id: Int = -1,
    val name: String = "",
    val latitude: Float = 0f,
    val longitude: Float = 0f,
) : Parcelable
