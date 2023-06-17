package com.binar.finalproject.model.otpcode


import com.google.gson.annotations.SerializedName

data class GetResendResponseOtp(
    @SerializedName("message")
    val message: String,
    @SerializedName("otp")
    val otp: String,
    @SerializedName("subject")
    val subject: String
)