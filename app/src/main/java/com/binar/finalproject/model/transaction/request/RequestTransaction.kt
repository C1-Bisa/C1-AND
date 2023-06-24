package com.binar.finalproject.model.transaction.request


import com.google.gson.annotations.SerializedName

data class RequestTransaction(
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("flights")
    val flights: List<Flight>,
    @SerializedName("passenger")
    val passenger: List<Passenger>
)