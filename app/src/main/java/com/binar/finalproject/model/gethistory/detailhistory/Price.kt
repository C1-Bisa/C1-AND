package com.binar.finalproject.model.gethistory.detailhistory


import com.google.gson.annotations.SerializedName

data class Price(
    @SerializedName("arrival")
    val arrival: Int,
    @SerializedName("departure")
    val departure: Int,
    @SerializedName("tax")
    val tax: Int,
    @SerializedName("totalPrice")
    val totalPrice: Int
)