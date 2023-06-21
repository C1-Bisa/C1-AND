package com.binar.finalproject.model.user.logout


import com.google.gson.annotations.SerializedName

data class ResponseLogout(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)