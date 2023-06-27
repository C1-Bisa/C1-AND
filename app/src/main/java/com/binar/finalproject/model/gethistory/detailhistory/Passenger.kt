package com.binar.finalproject.model.gethistory.detailhistory


import com.google.gson.annotations.SerializedName

data class Passenger(
    @SerializedName("birthday")
    val birthday: String,
    @SerializedName("expired")
    val expired: String,
    @SerializedName("family_name")
    val familyName: String,
    @SerializedName("issued_country")
    val issuedCountry: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("nationality")
    val nationality: String,
    @SerializedName("nik")
    val nik: Int,
    @SerializedName("seat")
    val seat: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: String
)