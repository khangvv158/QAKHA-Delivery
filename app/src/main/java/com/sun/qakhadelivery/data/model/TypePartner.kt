package com.sun.qakhadelivery.data.model

import com.google.gson.annotations.SerializedName

data class TypePartner(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String
)
