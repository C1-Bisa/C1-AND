package com.binar.finalproject.model.gethistory.detailhistory


import com.google.gson.annotations.SerializedName

data class ResponseDetailTransactionHistory(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)