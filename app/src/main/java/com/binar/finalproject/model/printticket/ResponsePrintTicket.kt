package com.binar.finalproject.model.printticket


import com.google.gson.annotations.SerializedName

data class ResponsePrintTicket(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)