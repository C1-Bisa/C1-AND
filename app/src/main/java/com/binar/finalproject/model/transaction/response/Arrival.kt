package com.binar.finalproject.model.transaction.response


import com.google.gson.annotations.SerializedName

data class Arrival(
    @SerializedName("Flight")
    val flightArrival: FlightArrival,
    @SerializedName("Transaction")
    val transaction: Transaction,
    @SerializedName("transaction_type")
    val transactionType: String
)