package com.binar.finalproject.model.flight


import com.google.gson.annotations.SerializedName

data class DataFlight(
    @SerializedName("flight")
    val flight: List<Flight>
)