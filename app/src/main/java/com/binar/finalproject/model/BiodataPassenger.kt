package com.binar.finalproject.model

data class BiodataPassenger(
    var ageCategory : String,
    var titlePassenger: String = "",
    var namePassenger : String = "",
    var nameClan : String = "",
    var birthDate : String = "",
    var citizen : String = "",
    var idCardOrPassport : String = "",
    var issuingCountry : String = ""
)
