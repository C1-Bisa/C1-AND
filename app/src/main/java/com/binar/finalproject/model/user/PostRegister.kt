package com.binar.finalproject.model.user


import com.google.gson.annotations.SerializedName

data class PostRegister(
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("phone")
    val phone: String
)