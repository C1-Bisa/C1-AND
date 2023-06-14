package com.binar.finalproject.model.searchflight


import com.google.gson.annotations.SerializedName

data class ResponseDataFlight(
    @SerializedName("data")
    val data: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)