package com.sun.qakhadelivery.data.model

import com.google.gson.annotations.SerializedName

data class TypePartner(
        @SerializedName("type_id") val id: Int,
        @SerializedName("name") val name: String
)
