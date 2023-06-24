package com.binar.finalproject.model.transaction.response


import com.google.gson.annotations.SerializedName

data class ResponseDataTransaction(
    @SerializedName("data")
    val dataTransaction: DataTransaction,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)