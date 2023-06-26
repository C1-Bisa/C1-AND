package com.binar.finalproject.model.notification.responsegetnotif


import com.google.gson.annotations.SerializedName

data class ResponseDataNotification(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)