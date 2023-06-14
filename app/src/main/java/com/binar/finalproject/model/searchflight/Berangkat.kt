package com.binar.finalproject.model.searchflight


import com.google.gson.annotations.SerializedName

data class Berangkat(
    @SerializedName("airline_id")
    val airlineId: Int,
    @SerializedName("airplane")
    val airplane: Airplane,
    @SerializedName("airport")
    val airport: Airport,
    @SerializedName("airport_id")
    val airportId: Int,
    @SerializedName("arrival_date")
    val arrivalDate: String,
    @SerializedName("arrival_time")
    val arrivalTime: String,
    @SerializedName("departure_date")
    val departureDate: String,
    @SerializedName("departure_time")
    val departureTime: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("flight_class")
    val flightClass: String,
    @SerializedName("from")
    val from: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("price")
    val price: Int,
    @SerializedName("to")
    val to: String
)