package com.binar.finalproject.model.gethistory


import com.google.gson.annotations.SerializedName

data class Transaction(
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("Flights")
    val flights: List<Flight>,
    @SerializedName("Passengers")
    val passengers: List<Passenger>,
    @SerializedName("transaction_code")
    val transactionCode: String,
    @SerializedName("transaction_date")
    val transactionDate: String,
    @SerializedName("transaction_status")
    val transactionStatus: String,
    @SerializedName("user_id")
    val userId: Int
)