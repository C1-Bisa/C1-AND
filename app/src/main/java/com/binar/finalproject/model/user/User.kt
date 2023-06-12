package com.binar.finalproject.model.user


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("isVerified")
    val isVerified: Boolean,
    @SerializedName("nama")
    val nama: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)