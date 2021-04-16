package com.sun.qakhadelivery.data.model

import com.google.gson.annotations.SerializedName

data class User(
        @SerializedName("name") val name: String?,
        @SerializedName("email") val email: String?,
        @SerializedName("address") val address: String?,
        @SerializedName("image") val image: Image?,
        @SerializedName("phone_number") val phoneNumber: String?,
        @SerializedName("role") val role: String?,
)
