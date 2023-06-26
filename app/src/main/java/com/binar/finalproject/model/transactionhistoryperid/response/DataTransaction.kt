package com.binar.finalproject.model.transactionhistoryperid.response


import com.google.gson.annotations.SerializedName

data class DataTransaction(
    @SerializedName("id")
    val id: Int,
    @SerializedName("Passengers")
    val passengers: List<DataPassenger>,
    @SerializedName("transaction_code")
    val transactionCode: String,
    @SerializedName("transaction_date")
    val transactionDate: String,
    @SerializedName("transaction_status")
    val transactionStatus: String,
    @SerializedName("user_id")
    val userId: Int
)