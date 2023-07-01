package com.binar.finalproject.model.printticket


import com.google.gson.annotations.SerializedName

data class RequestBodyPrintTicket(
    @SerializedName("transaction_id")
    val transactionId: String
)