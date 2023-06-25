package com.binar.finalproject.model.payment


import com.google.gson.annotations.SerializedName

data class RequestTransactionCode(
    @SerializedName("transaction_code")
    val transactionCode: String
)