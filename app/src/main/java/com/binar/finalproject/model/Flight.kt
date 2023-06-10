package com.binar.finalproject.model

data class Flight(
    val idFlight : Int,
    val airline : String,
    val seatClass : String,
    val from : String,
    val to : String,
    val timeDeparture : String,
    val timeArrive : String,
    val dateDeparture : String,
    val dateArrival : String,
    val price : String
)