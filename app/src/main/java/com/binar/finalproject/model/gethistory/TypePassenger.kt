package com.binar.finalproject.model.gethistory


import com.google.gson.annotations.SerializedName

data class TypePassenger(
    @SerializedName("adult")
    val adult: Int,
    @SerializedName("child")
    val child: Int
)