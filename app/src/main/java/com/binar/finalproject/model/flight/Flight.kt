package com.binar.finalproject.model.flight


import com.google.gson.annotations.SerializedName

data class Flight(
    @SerializedName("Airline")
    val airline: Airline,
    @SerializedName("airline_id")
    val airlineId: Int,
    @SerializedName("Airport")
    val airport: Airport,
    @SerializedName("airport_id")
    val airportId: Int,
    @SerializedName("arrival_date")
    val arrivalDate: String,
    @SerializedName("arrival_time")
    val arrivalTime: String,
    @SerializedName("createdAt")
    val createdAt: String,
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
    val to: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)