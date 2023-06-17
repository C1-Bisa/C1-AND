package com.binar.finalproject.model.user.profile


import com.google.gson.annotations.SerializedName

data class ResponseUserProfile(
    @SerializedName("data")
    val data: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)