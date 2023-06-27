package com.binar.finalproject.model.gethistory


import com.google.gson.annotations.SerializedName

data class ResponseHistory(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)