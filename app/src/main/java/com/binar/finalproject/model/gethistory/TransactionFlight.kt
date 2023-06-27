package com.binar.finalproject.model.gethistory


import com.google.gson.annotations.SerializedName

data class TransactionFlight(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("flight_id")
    val flightId: Int,
    @SerializedName("transaction_id")
    val transactionId: Int,
    @SerializedName("transaction_type")
    val transactionType: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)