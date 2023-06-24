package com.binar.finalproject.model.transaction.response


import com.google.gson.annotations.SerializedName

data class Transaction(
    @SerializedName("amount")
    val amount: Int
)