package com.binar.finalproject.model.notification.updatenotif


import com.google.gson.annotations.SerializedName

data class ResponseUpdateNotif(
    @SerializedName("data")
    val `data`: List<Int>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)