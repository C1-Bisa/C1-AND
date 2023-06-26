package com.binar.finalproject.model.transactionhistoryperid.response


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("arrival")
    val arrival: Arrival?,
    @SerializedName("departure")
    val departure: Departure,
    @SerializedName("passenger")
    val passenger: Passenger,
    @SerializedName("price")
    val price: Price,
    @SerializedName("transaction")
    val transaction: DataTransaction
)