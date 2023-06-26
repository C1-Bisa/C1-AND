package com.binar.finalproject.model.notification.responsegetnotif


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("headNotif")
    val headNotif: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("isRead")
    val isRead: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("userId")
    val userId: Int
)