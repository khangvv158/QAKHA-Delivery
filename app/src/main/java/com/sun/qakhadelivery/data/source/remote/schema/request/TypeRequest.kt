package com.sun.qakhadelivery.data.source.remote.schema.request

import com.google.gson.annotations.SerializedName

data class TypeRequest(
    @SerializedName("type_id") val idType: Int
)
