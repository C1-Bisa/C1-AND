package com.binar.finalproject.model.transaction


import com.google.gson.annotations.SerializedName

data class Passenger(
    @SerializedName("birthday")
    var birthday: String,
    @SerializedName("expired")
    var expired: String,
    @SerializedName("family_name")
    var familyName: String,
    @SerializedName("issued_country")
    var issuedCountry: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("nationality")
    var nationality: String,
    @SerializedName("nik")
    var nik: Int,
    @SerializedName("seat")
    var seat: String,
    @SerializedName("title")
    var title: String,
    @SerializedName("type")
    var type: String
)