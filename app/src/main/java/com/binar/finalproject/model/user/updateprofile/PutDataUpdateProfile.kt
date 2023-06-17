package com.binar.finalproject.model.user.updateprofile


import com.google.gson.annotations.SerializedName

data class PutDataUpdateProfile(
    @SerializedName("nama")
    val nama: String,
    @SerializedName("phone")
    val phone: String
)