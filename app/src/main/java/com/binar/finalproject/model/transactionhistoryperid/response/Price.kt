package com.binar.finalproject.model.transactionhistoryperid.response


import com.google.gson.annotations.SerializedName

data class Price(
    @SerializedName("departure")
    val departure: Int,
    @SerializedName("arrival")
    val arrival: Int,
    @SerializedName("tax")
    val tax: Int,
    @SerializedName("totalPrice")
    val totalPrice: Int
)