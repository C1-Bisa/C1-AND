package com.binar.finalproject.model.transaction


import com.google.gson.annotations.SerializedName

data class PostCreateTransaction(
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("flight_id")
    val flightId: List<Int>,
    @SerializedName("passenger")
    val passenger: List<Passenger>
)