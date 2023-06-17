package com.binar.finalproject.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binar.finalproject.model.otpcode.PutDataOtp
import com.binar.finalproject.model.otpcode.ResponseOtp
import com.binar.finalproject.model.resetpassword.PatchResetPassword
import com.binar.finalproject.model.resetpassword.ResponseResetPassword
import com.binar.finalproject.model.user.PostRegister
import com.binar.finalproject.model.user.ResponRegister
import com.binar.finalproject.model.user.login.PostLogin
import com.binar.finalproject.model.user.login.ResponseLogin
import com.binar.finalproject.model.user.profile.ResponseUserProfile
import com.binar.finalproject.model.user.updateprofile.PutDataUpdateProfile
import com.binar.finalproject.model.user.updateprofile.ResponseUpdateProfileUser
import com.binar.finalproject.network.RestfulApi
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val apiUser : RestfulApi) : ViewModel(){


    //mutablelive data untuk Login
    private val _responseResetPassword = MutableLiveData<ResponseResetPassword?>()
    val responseResetPassword : LiveData<ResponseResetPassword?> = _responseResetPassword

    //mutablelive data untuk Login
    private val _responseLogin = MutableLiveData<ResponseLogin?>()
    val responseLogin : LiveData<ResponseLogin?> = _responseLogin

    //mutablelive data untuk OTP
    private val _responseOtp = MutableLiveData<ResponseOtp?>()
    val responseOtp : LiveData<ResponseOtp?> = _responseOtp

    //mutablelive data untuk register
    private val _responseUserRegist = MutableLiveData<ResponRegister?>()
    val responseUserRegist : LiveData<ResponRegister?> = _responseUserRegist

    //live data untuk data response getprofile
    private val _responseDataProfile = MutableLiveData<ResponseUserProfile?>()
    val responseDataProfile : LiveData<ResponseUserProfile?> = _responseDataProfile

    //live data untuk data response updateprofile
    private val _responseUpdateProfile = MutableLiveData<ResponseUpdateProfileUser?>()
    val responseUpdateProfile : LiveData<ResponseUpdateProfileUser?> = _responseUpdateProfile

    // viewmodel untuk otp
    fun putVerificationOtp(otp : PutDataOtp){
        apiUser.verificationOTP(otp).enqueue(object : Callback<ResponseOtp>{
            override fun onResponse(call: Call<ResponseOtp>, response: Response<ResponseOtp>) {
                if (response.isSuccessful){
                    _responseOtp.postValue(response.body()!!)
                    Log.i("STATUS", response.body()!!.message)
                }else{
                    _responseOtp.postValue(null)
                }
            }

            override fun onFailure(call: Call<ResponseOtp>, t: Throwable) {
                _responseOtp.postValue(null)
            }

        })
    }
    // viewmodel untuk reset password
    fun patchResetPassword(resetPassword : PatchResetPassword){
        apiUser.resetPassword(resetPassword).enqueue(object : Callback<ResponseResetPassword>{
            override fun onResponse(
                call: Call<ResponseResetPassword>,
                response: Response<ResponseResetPassword>
            ) {
                if (response.isSuccessful){
                    _responseResetPassword.postValue(response.body()!!)
                    Log.i("STATUS", response.body()!!.message)

                }else{
                    _responseOtp.postValue(null)
                }
            }

            override fun onFailure(call: Call<ResponseResetPassword>, t: Throwable) {
                _responseOtp.postValue(null)
            }

        })
    }

    // viewmodel untuk login
    fun postLogin(data : PostLogin){
        apiUser.postLogin(data).enqueue(object : Callback<ResponseLogin>{
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                if (response.isSuccessful){
                    _responseLogin.postValue(response.body()!!)
                    Log.i("STATUS", response.body()!!.status)
                }else{
                    _responseLogin.postValue(null)
                }
            }

            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                _responseLogin.postValue(null)
            }

        })
    }



    // untuk viewmodel register
    fun postRegist(data : PostRegister){
        apiUser.postRegistUser(data).enqueue(object : Callback<ResponRegister>{
            override fun onResponse(
                call: Call<ResponRegister>,
                response: Response<ResponRegister>
            ) {
               if (response.isSuccessful){
                   _responseUserRegist.postValue(response.body()!!)
                   Log.i("STATUS", response.body()!!.status.toString())
               }else{
                   _responseUserRegist.postValue(null)
               }
            }

            override fun onFailure(call: Call<ResponRegister>, t: Throwable) {
                _responseUserRegist.postValue(null)
            }

        })
    }

    //untuk get user profile
    fun getUserProfile(token : String){
        apiUser.getProfileUser(tokenUser = "Bearer $token").enqueue(object : Callback<ResponseUserProfile>{
            override fun onResponse(
                call: Call<ResponseUserProfile>,
                response: Response<ResponseUserProfile>
            ) {
                if(response.isSuccessful){
                    _responseDataProfile.postValue(response.body())
                }else{
                    _responseDataProfile.postValue(null)
                }
            }

            override fun onFailure(call: Call<ResponseUserProfile>, t: Throwable) {
                _responseDataProfile.postValue(null)
            }

        })
    }

    //untuk update data user
    fun updateProfileUser(token: String, dataUser : PutDataUpdateProfile){
        apiUser.updateUserProfile(tokenUser = "Bearer $token", dataUser).enqueue(object : Callback<ResponseUpdateProfileUser>{
            override fun onResponse(
                call: Call<ResponseUpdateProfileUser>,
                response: Response<ResponseUpdateProfileUser>
            ) {
                if(response.isSuccessful){
                    _responseUpdateProfile.postValue(response.body())
                }else{
                    _responseUpdateProfile.postValue(null)
                }
            }

            override fun onFailure(call: Call<ResponseUpdateProfileUser>, t: Throwable) {
                _responseUpdateProfile.postValue(null)
            }

        })
    }



}