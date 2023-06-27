package com.binar.finalproject.model.gethistory


import com.google.gson.annotations.SerializedName

data class Price(
    @SerializedName("arrival")
    val arrival: Int,
    @SerializedName("departure")
    val departure: Int,
    @SerializedName("tax")
    val tax: Int,
    @SerializedName("total")
    val total: Int
)