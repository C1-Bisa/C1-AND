package com.binar.finalproject.model.gethistory.detailhistory


import com.google.gson.annotations.SerializedName

data class Departure(
    @SerializedName("departure")
    val departure: DepartureX,
    @SerializedName("passanger")
    val passanger: Passanger,
    @SerializedName("price")
    val price: Price,
    @SerializedName("Transaction")
    val transaction: TransactionX,
    @SerializedName("transaction_type")
    val transactionType: String
)