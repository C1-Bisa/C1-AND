package com.binar.finalproject.model.gethistory.detailhistory


import com.google.gson.annotations.SerializedName

data class Passanger(
    @SerializedName("adult")
    val adult: Int,
    @SerializedName("child")
    val child: Int
)