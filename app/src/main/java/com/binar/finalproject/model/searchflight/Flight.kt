package com.binar.finalproject.model.searchflight


import com.google.gson.annotations.SerializedName

data class Flight(
    @SerializedName("airlane_code")
    val airlaneCode: String,
    @SerializedName("airline")
    val airline: String,
    @SerializedName("airline_id")
    val airlineId: Int,
    @SerializedName("airport_from")
    val airportFrom: String,
    @SerializedName("airport_from_code")
    val airportFromCode: String,
    @SerializedName("airport_id_from")
    val airportIdFrom: Int,
    @SerializedName("airport_id_to")
    val airportIdTo: Int,
    @SerializedName("airport_to")
    val airportTo: String,
    @SerializedName("airport_to_code")
    val airportToCode: String,
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
    @SerializedName("duration")
    val duration: Int,
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
) : java.io.Serializable