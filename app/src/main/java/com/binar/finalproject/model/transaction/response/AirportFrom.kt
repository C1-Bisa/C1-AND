package com.binar.finalproject.model.transaction.response


import com.google.gson.annotations.SerializedName

data class AirportFrom(
    @SerializedName("airport_code")
    val airportCode: String,
    @SerializedName("airport_location")
    val airportLocation: String,
    @SerializedName("airport_name")
    val airportName: String
)