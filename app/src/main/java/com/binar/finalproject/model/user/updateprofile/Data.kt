package com.binar.finalproject.model.user.updateprofile


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("email")
    val email: String,
    @SerializedName("nama")
    val nama: String,
    @SerializedName("phone")
    val phone: String
)