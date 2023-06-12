package com.binar.finalproject.network

import com.binar.finalproject.model.airport.ResponseDataAirport
import com.binar.finalproject.model.user.PostRegister
import com.binar.finalproject.model.user.ResponRegister
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RestfulApi {
    //ketika sudah ada token butuh header token barier sebagai authorized
    @GET("airport")
    fun getAllDataAirport() : Call<ResponseDataAirport>

    @POST("user/register")
    fun postRegistUser(@Body data :  PostRegister) : Call<ResponRegister>

}