package com.binar.finalproject.model.resetpassword


import com.google.gson.annotations.SerializedName

data class PatchResetPassword(
    @SerializedName("email")
    val email: String
)