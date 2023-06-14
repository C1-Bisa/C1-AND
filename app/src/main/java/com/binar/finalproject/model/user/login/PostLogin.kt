package com.binar.finalproject.model.user.login


import com.google.gson.annotations.SerializedName

data class PostLogin(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)