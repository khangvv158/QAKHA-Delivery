package com.sun.qakhadelivery.data.source.remote.schema.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VerifyEmail(
    @SerializedName("new_email") val new_email: String,
    @SerializedName("code_activate") val code_activate: String,
) : Parcelable
