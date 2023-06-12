package com.binar.finalproject.model.airport


import com.binar.finalproject.model.airport.DataAirport
import com.google.gson.annotations.SerializedName

data class ResponseDataAirport(
    @SerializedName("data")
    val dataAirport: DataAirport,
    @SerializedName("status")
    val status: String
)