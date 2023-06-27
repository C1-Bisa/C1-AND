package com.binar.finalproject.model.gethistory.detailhistory


import com.google.gson.annotations.SerializedName

data class DepartureX(
    @SerializedName("Flight")
    val flight: Flight,
    @SerializedName("Transaction")
    val transaction: TransactionX,
    @SerializedName("transaction_type")
    val transactionType: String
)