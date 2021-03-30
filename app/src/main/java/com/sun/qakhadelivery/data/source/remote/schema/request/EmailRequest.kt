package com.sun.qakhadelivery.data.source.remote.schema.request

import com.google.gson.annotations.SerializedName

data class EmailRequest(
        @SerializedName("email") val email: String
)
