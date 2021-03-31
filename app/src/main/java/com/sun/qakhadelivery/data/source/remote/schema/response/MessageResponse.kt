package com.sun.qakhadelivery.data.source.remote.schema.response

import com.google.gson.annotations.SerializedName

data class MessageResponse(
        @SerializedName("message") val message: String
)
