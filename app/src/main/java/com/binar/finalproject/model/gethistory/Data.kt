package com.binar.finalproject.model.gethistory


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("price")
    val price: Price,
    @SerializedName("transaction")
    val transaction: Transaction,
    @SerializedName("type_passenger")
    val typePassenger: TypePassenger
)