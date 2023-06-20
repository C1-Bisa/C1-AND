package com.binar.finalproject.model.user.login


import com.google.gson.annotations.SerializedName

data class ResponseLogin(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("status")
    val status: String

)