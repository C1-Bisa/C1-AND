package com.binar.finalproject.model.payment


import com.google.gson.annotations.SerializedName

data class ResponsePayment(
    @SerializedName("data")
    val `data`: List<Int>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)