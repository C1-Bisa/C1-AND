package com.binar.finalproject.model.transaction.response


import com.google.gson.annotations.SerializedName

data class Departure(
    @SerializedName("Flight")
    val flight: FlightDeparture,
    @SerializedName("Transaction")
    val transaction: Transaction,
    @SerializedName("transaction_type")
    val transactionType: String
)