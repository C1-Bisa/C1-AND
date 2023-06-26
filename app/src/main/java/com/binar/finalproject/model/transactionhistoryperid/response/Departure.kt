package com.binar.finalproject.model.transactionhistoryperid.response


import com.google.gson.annotations.SerializedName

data class Departure(
    @SerializedName("Flight")
    val flight: Flight,
    @SerializedName("Transaction")
    val transaction: Transaction,
    @SerializedName("transaction_type")
    val transactionType: String
)