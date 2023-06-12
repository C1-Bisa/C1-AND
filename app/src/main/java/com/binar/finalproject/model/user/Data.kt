package com.binar.finalproject.model.user


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("otp")
    val otp: String,
    @SerializedName("user")
    val user: User
)