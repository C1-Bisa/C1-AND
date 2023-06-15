package com.binar.finalproject.model.resetpassword


import com.google.gson.annotations.SerializedName

data class ResponseResetPassword(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)