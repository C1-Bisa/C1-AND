package com.binar.finalproject.model.user


import com.google.gson.annotations.SerializedName

data class ResponRegister(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)