package com.binar.finalproject.model.transactionhistoryperid.response


import com.google.gson.annotations.SerializedName

data class AirportTo(
    @SerializedName("airport_code")
    val airportCode: String,
    @SerializedName("airport_location")
    val airportLocation: String,
    @SerializedName("airport_name")
    val airportName: String
)