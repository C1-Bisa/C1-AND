package com.binar.finalproject.network

import com.binar.finalproject.model.airport.ResponseDataAirport
import retrofit2.Call
import retrofit2.http.GET

interface RestfulApi {
    //ketika sudah ada token butuh header token barier sebagai authorized
    @GET("airport")
    fun getAllDataAirport() : Call<ResponseDataAirport>
}