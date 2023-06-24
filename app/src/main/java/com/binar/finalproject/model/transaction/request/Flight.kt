package com.binar.finalproject.model.transaction.request


import com.google.gson.annotations.SerializedName

data class Flight(
    @SerializedName("flight_id")
    val flightId: Int,
    @SerializedName("flight_type")
    val flightType: String
)