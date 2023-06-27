package com.binar.finalproject.model.gethistory.detailhistory


import com.google.gson.annotations.SerializedName

data class PostTransactionHistory(
    @SerializedName("transaction_id")
    val transactionId: Int
)