package com.binar.finalproject.model.resetpassword.createnewpassword


import com.google.gson.annotations.SerializedName

data class PutCreateNewPassword(
    @SerializedName("newPassword")
    val newPassword: String
)