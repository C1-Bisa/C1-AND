package com.binar.finalproject.model.transaction.response


import com.google.gson.annotations.SerializedName

data class Passenger(
    @SerializedName("name")
    val name: String,
    @SerializedName("nik_paspor")
    val nikPaspor: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("transactionCode")
    val transactionCode: String,
    @SerializedName("type")
    val type: String
)