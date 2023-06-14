package com.binar.finalproject.model.searchflight


import com.google.gson.annotations.SerializedName

data class Flight(
    @SerializedName("berangkat")
    val berangkat: List<Berangkat>,
    @SerializedName("pulang")
    val pulang: List<Any>
)