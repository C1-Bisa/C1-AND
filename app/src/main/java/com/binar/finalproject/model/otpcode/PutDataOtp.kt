package com.binar.finalproject.model.otpcode


import com.google.gson.annotations.SerializedName

data class PutDataOtp(
    @SerializedName("OTPinput")
    val oTPinput: String
)