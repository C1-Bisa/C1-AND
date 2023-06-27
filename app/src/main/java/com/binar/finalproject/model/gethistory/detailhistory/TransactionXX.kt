package com.binar.finalproject.model.gethistory.detailhistory


import com.google.gson.annotations.SerializedName

data class TransactionXX(
    @SerializedName("id")
    val id: Int,
    @SerializedName("Passengers")
    val passengers: List<Passenger>,
    @SerializedName("transactionCode")
    val transactionCode: String,
    @SerializedName("transaction_date")
    val transactionDate: String,
    @SerializedName("transaction_status")
    val transactionStatus: String,
    @SerializedName("user_id")
    val userId: Int
)