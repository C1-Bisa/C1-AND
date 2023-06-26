package com.binar.finalproject.model.transactionhistoryperid.request


import com.google.gson.annotations.SerializedName

data class RequestTransactionId(
    @SerializedName("transaction_id")
    val transactionId: Int
)