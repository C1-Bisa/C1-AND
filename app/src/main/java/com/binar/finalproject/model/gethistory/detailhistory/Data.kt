package com.binar.finalproject.model.gethistory.detailhistory


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("departure")
    val departure: Departure,
    @SerializedName("transaction")
    val transaction: TransactionXX
)