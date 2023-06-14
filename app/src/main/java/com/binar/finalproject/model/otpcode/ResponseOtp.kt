package com.binar.finalproject.model.otpcode


import com.google.gson.annotations.SerializedName

data class ResponseOtp(
    @SerializedName("message")
    val message: String,
    @SerializedName("subject")
    val subject: String
)