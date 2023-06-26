package com.binar.finalproject.model.transactionhistoryperid.response


import com.google.gson.annotations.SerializedName

data class Airline(
    @SerializedName("airline_code")
    val airlineCode: String,
    @SerializedName("airline_name")
    val airlineName: String
)