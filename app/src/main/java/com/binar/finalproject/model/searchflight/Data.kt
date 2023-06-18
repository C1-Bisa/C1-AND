package com.binar.finalproject.model.searchflight


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("flight")
    val flight: List<Flight>
)