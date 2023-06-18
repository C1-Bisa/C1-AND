package com.binar.finalproject.model.searchflight


import com.google.gson.annotations.SerializedName

data class PostSearchFlight(
    @SerializedName("departure_date")
    var departureDate: String,
    @SerializedName("departure_time")
    var departureTime: String,
    @SerializedName("flight_class")
    var flightClass: String,
    @SerializedName("from")
    var from: String,
    @SerializedName("to")
    var to: String
)