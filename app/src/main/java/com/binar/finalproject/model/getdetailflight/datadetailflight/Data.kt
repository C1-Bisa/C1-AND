package com.binar.finalproject.model.getdetailflight.datadetailflight


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("berangkat")
    val berangkat: Berangkat,
    @SerializedName("pulang")
    val pulang: Pulang,
    @SerializedName("tax")
    val tax: Int,
    @SerializedName("totalAdults")
    val totalAdults: Int,
    @SerializedName("totalBaby")
    val totalBaby: Int,
    @SerializedName("totalChild")
    val totalChild: Int,
    @SerializedName("totalPrice")
    val totalPrice: Int
)