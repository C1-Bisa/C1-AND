package com.binar.finalproject.model.getdetailflight.datadetailflight


import com.google.gson.annotations.SerializedName

data class ResponseDetailFlight(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("status")
    val status: String
)