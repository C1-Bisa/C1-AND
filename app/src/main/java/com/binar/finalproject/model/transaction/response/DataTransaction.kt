package com.binar.finalproject.model.transaction.response


import com.google.gson.annotations.SerializedName

data class DataTransaction(
    @SerializedName("arrival")
    val arrival: List<Arrival>,
    @SerializedName("departure")
    val departure: List<Departure>,
    @SerializedName("transaction")
    val transaction: TransactionUser
) : java.io.Serializable