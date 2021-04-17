package com.sun.qakhadelivery.data.model

import com.google.gson.annotations.SerializedName

data class Feedback(
    @SerializedName("feedbacks") val comments: MutableList<Comment>,
    @SerializedName("avg_point") val avgPoint: Float
)

data class Comment(
    @SerializedName("id") val idFeedback: Int,
    @SerializedName("content") val content: String,
    @SerializedName("point") val point: Int,
    @SerializedName("user_id") val idUser: Int,
    @SerializedName("order_id") val idOrder: Int,
    @SerializedName("driver_id") val idDriver: Int,
    @SerializedName("partner_id") val idPartner: Int,
    @SerializedName("created_at") val timeCreate: String,
    @SerializedName("updated_at") val timeUpdate: String,
    @SerializedName("user") val user: UserComment
)

data class UserComment(
    @SerializedName("name") val name: String?,
    @SerializedName("image") val image: Image?
)
