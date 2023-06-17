package com.binar.finalproject.model.user.updateprofile


import com.google.gson.annotations.SerializedName

data class ResponseUpdateProfileUser(
    @SerializedName("data")
    val data: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)