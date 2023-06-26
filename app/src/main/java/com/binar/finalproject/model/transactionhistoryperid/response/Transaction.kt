package com.binar.finalproject.model.transactionhistoryperid.response


import com.google.gson.annotations.SerializedName

data class Transaction(
    @SerializedName("amount")
    val amount: Int
)