package com.binar.finalproject.model.airport


import com.binar.finalproject.model.airport.Airport
import com.google.gson.annotations.SerializedName

data class DataAirport(
    @SerializedName("airport")
    val airport: List<Airport>
)