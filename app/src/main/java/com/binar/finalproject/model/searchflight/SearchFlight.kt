package com.binar.finalproject.model.searchflight


import com.google.gson.annotations.SerializedName

data class SearchFlight(
    @SerializedName("departure_date")
    var departureDate: String = "",
    @SerializedName("departure_time")
    var departureTime: String = "",
    @SerializedName("from")
    var from: String = "",
    @SerializedName("returnDate")
    var returnDate: String = "",
    @SerializedName("to")
    var to: String = "",
    @SerializedName("flight_class")
    var flightClass: String = ""
) : java.io.Serializable