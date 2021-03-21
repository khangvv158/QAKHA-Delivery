package com.sun.qakhadelivery.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Image(
    val imageUrl: String,
    val imageId: String,
    val imageType: String
) : Parcelable
