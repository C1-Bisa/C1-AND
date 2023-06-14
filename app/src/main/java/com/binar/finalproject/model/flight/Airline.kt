package com.binar.finalproject.model.flight


import com.google.gson.annotations.SerializedName

data class Airline(
    @SerializedName("airline_code")
    val airlineCode: String,
    @SerializedName("airline_name")
    val airlineName: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("updatedAt")
    val updatedAt: String
)