package com.binar.finalproject.model.getdetailflight


import com.google.gson.annotations.SerializedName

data class PostDataFlight(
    @SerializedName("anak")
    val anak: Int,
    @SerializedName("bayi")
    val bayi: Int,
    @SerializedName("dewasa")
    val dewasa: Int,
    @SerializedName("flight_id")
    val flightId: List<Int>
)