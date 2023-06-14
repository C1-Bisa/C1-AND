package com.binar.finalproject.network

import com.binar.finalproject.model.airport.ResponseDataAirport
import com.binar.finalproject.model.otpcode.PutDataOtp
import com.binar.finalproject.model.otpcode.ResponseOtp
import com.binar.finalproject.model.searchflight.ResponseDataFlight
import com.binar.finalproject.model.searchflight.SearchFlight
import com.binar.finalproject.model.user.PostRegister
import com.binar.finalproject.model.user.ResponRegister
import com.binar.finalproject.model.user.login.PostLogin
import com.binar.finalproject.model.user.login.ResponseLogin
import retrofit2.Call
import retrofit2.http.*

interface RestfulApi {
    //ketika sudah ada token butuh header token barier sebagai authorized
    @GET("airport")
    fun getAllDataAirport() : Call<ResponseDataAirport>

    @POST("user/register")
    fun postRegistUser(@Body data :  PostRegister) : Call<ResponRegister>

    @POST("user/login")
    fun postLogin(@Body data : PostLogin) : Call<ResponseLogin>

    //sementara buat get data flight
    @POST("flight/searchflight")
    fun getSearchDataFlight(@Body data: SearchFlight) : Call<ResponseDataFlight>

    @PUT("user/verification")
    fun verificationOTP(@Body otp : PutDataOtp) : Call<ResponseOtp>

}