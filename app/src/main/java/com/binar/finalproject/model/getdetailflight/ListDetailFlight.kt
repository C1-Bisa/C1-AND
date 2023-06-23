package com.binar.finalproject.model.getdetailflight

import com.binar.finalproject.model.getdetailflight.roundtrip.Airline
import com.binar.finalproject.model.getdetailflight.roundtrip.AirportFrom
import com.binar.finalproject.model.getdetailflight.roundtrip.AirportTo
import com.google.gson.annotations.SerializedName

data class ListDetailFlight (
        @SerializedName("Airline")
        val airline: Airline,
        @SerializedName("Airport_from")
        val airportFrom: AirportFrom,
        @SerializedName("Airport_to")
        val airportTo: AirportTo,
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
        @SerializedName("price")
        val price: Int,
        @SerializedName("to")
        val to: String
        )