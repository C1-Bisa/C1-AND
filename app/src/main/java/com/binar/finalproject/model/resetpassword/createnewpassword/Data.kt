package com.binar.finalproject.model.resetpassword.createnewpassword

import com.google.gson.annotations.SerializedName

data class Data(

    @SerializedName("email")
    val email: String,
    @SerializedName("nama")
    val nama: String,


)
