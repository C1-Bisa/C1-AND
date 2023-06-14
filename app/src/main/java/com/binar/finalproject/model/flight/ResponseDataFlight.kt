package com.binar.finalproject.model.flight


import com.google.gson.annotations.SerializedName

data class ResponseDataFlight(
    @SerializedName("data")
    val dataFlight: DataFlight,
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("status")
    val status: String
)