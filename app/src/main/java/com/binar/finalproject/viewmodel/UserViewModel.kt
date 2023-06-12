package com.binar.finalproject.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binar.finalproject.model.airport.Airport
import com.binar.finalproject.model.user.PostRegister
import com.binar.finalproject.model.user.ResponRegister
import com.binar.finalproject.network.RestfulApi
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val apiUser : RestfulApi) : ViewModel(){

    private val _responseUserRegist = MutableLiveData<ResponRegister?>()
    val responseUserRegist : LiveData<ResponRegister?> = _responseUserRegist

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




}