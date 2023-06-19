package com.binar.finalproject.model.resetpassword.createnewpassword


import com.google.gson.annotations.SerializedName

data class ResponseCreateNewPassword(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)