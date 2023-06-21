package com.binar.finalproject.network

import com.binar.finalproject.model.airport.ResponseDataAirport
import com.binar.finalproject.model.otpcode.GetResendResponseOtp
import com.binar.finalproject.model.otpcode.PutDataOtp
import com.binar.finalproject.model.otpcode.ResponseOtp
import com.binar.finalproject.model.resetpassword.PatchResetPassword
import com.binar.finalproject.model.resetpassword.ResponseResetPassword
import com.binar.finalproject.model.searchflight.PostSearchFlight
import com.binar.finalproject.model.searchflight.ResponseDataFlight
import com.binar.finalproject.model.searchflight.SearchFlight
import com.binar.finalproject.model.user.PostRegister
import com.binar.finalproject.model.user.ResponRegister
import com.binar.finalproject.model.user.login.PostLogin
import com.binar.finalproject.model.user.login.ResponseLogin
import com.binar.finalproject.model.user.profile.ResponseUserProfile
import com.binar.finalproject.model.user.updateprofile.PutDataUpdateProfile
import com.binar.finalproject.model.user.updateprofile.ResponseUpdateProfileUser
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
    //dapat diganti dengan query map
    @POST("flight/searchflight")
    fun getSearchDataFlight(
        @Body data: PostSearchFlight,
        @QueryMap filter : Map<String, Boolean> = mapOf("toLower" to false)
    ) : Call<ResponseDataFlight>

    @PUT("user/verification")
    fun verificationOTP(@Body otp : PutDataOtp) : Call<ResponseOtp>

    @POST("user/resetPassword")
    fun resetPassword(@Body resetPassword : PatchResetPassword) : Call<ResponseResetPassword>

    //MENGGUNAKAN ENDPOINT TERBARU
    @GET("user/getProfile")
    fun getProfileUser(@Header("Authorization") tokenUser: String) : Call<ResponseUserProfile>

    @PUT("user/update")
    fun updateUserProfile(@Header("Authorization") tokenUser: String, @Body putDataUser : PutDataUpdateProfile) : Call<ResponseUpdateProfileUser>

    @GET("user/resendcode/{userId}")
    fun getResendOtp(@Path("userId") id : Int) : Call<GetResendResponseOtp>

}