package com.binar.finalproject.model.transactionhistoryperid.response


import com.google.gson.annotations.SerializedName

data class Passenger(
    @SerializedName("adult")
    val adult: Int,
    @SerializedName("child")
    val child: Int
)