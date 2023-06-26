package com.binar.finalproject.network

import com.binar.finalproject.model.airport.ResponseDataAirport
import com.binar.finalproject.model.getdetailflight.PostDataFlight
import com.binar.finalproject.model.getdetailflight.datadetailflight.ResponseDetailFlight
import com.binar.finalproject.model.notification.responsegetnotif.ResponseDataNotification
import com.binar.finalproject.model.notification.updatenotif.ResponseUpdateNotif
import com.binar.finalproject.model.otpcode.GetResendResponseOtp
import com.binar.finalproject.model.otpcode.PutDataOtp
import com.binar.finalproject.model.otpcode.ResponseOtp
import com.binar.finalproject.model.payment.RequestTransactionCode
import com.binar.finalproject.model.payment.ResponsePayment
import com.binar.finalproject.model.resetpassword.PatchResetPassword
import com.binar.finalproject.model.resetpassword.ResponseResetPassword
import com.binar.finalproject.model.resetpassword.createnewpassword.PutCreateNewPassword
import com.binar.finalproject.model.resetpassword.createnewpassword.ResponseCreateNewPassword
import com.binar.finalproject.model.searchflight.PostSearchFlight
import com.binar.finalproject.model.searchflight.ResponseDataFlight
import com.binar.finalproject.model.searchflight.SearchFlight
import com.binar.finalproject.model.transaction.request.RequestTransaction
import com.binar.finalproject.model.transaction.response.ResponseDataTransaction
import com.binar.finalproject.model.transactionhistoryperid.request.RequestTransactionId
import com.binar.finalproject.model.transactionhistoryperid.response.ResponseTransactionPerId
import com.binar.finalproject.model.user.PostRegister
import com.binar.finalproject.model.user.ResponRegister
import com.binar.finalproject.model.user.login.PostLogin
import com.binar.finalproject.model.user.login.ResponseLogin
import com.binar.finalproject.model.user.logout.ResponseLogout
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

    @POST("user/logout")
    fun postLogout() : Call<ResponseLogout>

    //sementara buat get data flight
    //dapat diganti dengan query map
    @POST("flight/searchflight")
    fun getSearchDataFlight(
        @Body data: PostSearchFlight,
        @QueryMap filter : Map<String, Boolean> = mapOf("toLower" to false)
    ) : Call<ResponseDataFlight>


    @POST("flight/getDetail")
    fun getDetailFlight(
        @Body data : PostDataFlight
    ) : Call<ResponseDetailFlight>

    //for transaction
    @POST("transaction")
    fun createTransaction(
        @Header("Authorization") tokenUser: String,
        @Body data : RequestTransaction
    ) : Call<ResponseDataTransaction>

    //payment
    @PUT("transaction/update")
    fun paymentTicketFlight(
        @Header("Authorization") tokenUser: String,
        @Body codeTransaction: RequestTransactionCode
    ) : Call<ResponsePayment>

    //get data transaction per ID transaction
    @POST("transaction/getById")
    fun getDataTrasactionById(
        @Header("Authorization") tokenUser: String,
        @Body idTransacction: RequestTransactionId
    ) : Call<ResponseTransactionPerId>

    //get all notification
    @GET("notification")
    fun getNotification(
        @Header("Authorization") tokenUser: String
    ) : Call<ResponseDataNotification>

    @GET("notification/update")
    fun UpdateReadNotification(
        @Header("Authorization") tokenUser: String
    ) : Call<ResponseUpdateNotif>


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

    @PUT("user/createNewPassword/{userId}/{otp}")
    fun putCreateNewPassword(@Body createNewPasw : PutCreateNewPassword) : Call<ResponseCreateNewPassword>

}