package com.binar.finalproject.model.transactionhistoryperid.response


import com.google.gson.annotations.SerializedName

data class DataPassenger(
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