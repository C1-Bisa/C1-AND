package com.binar.finalproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binar.finalproject.model.notification.responsegetnotif.Data
import com.binar.finalproject.model.notification.responsegetnotif.ResponseDataNotification
import com.binar.finalproject.model.notification.updatenotif.ResponseUpdateNotif
import com.binar.finalproject.model.searchflight.Flight
import com.binar.finalproject.network.RestfulApi
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(private val api : RestfulApi) : ViewModel() {

    private val _responseNotif = MutableLiveData<List<Data>>()
    val responseNotif : LiveData<List<Data>> = _responseNotif

    private val _responseUpdateNotif = MutableLiveData<ResponseUpdateNotif?>()
    val responseUpdateNotif : LiveData<ResponseUpdateNotif?> = _responseUpdateNotif


    fun getNotification(token : String){
        api.getNotification(tokenUser = "Bearer $token").enqueue(object : Callback<ResponseDataNotification>{
            override fun onResponse(
                call: Call<ResponseDataNotification>,
                response: Response<ResponseDataNotification>
            ) {
                if(response.isSuccessful){
                    _responseNotif.postValue(response.body()!!.data)
                }else{
                    _responseNotif.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<ResponseDataNotification>, t: Throwable) {
                _responseNotif.postValue(emptyList())
            }

        })
    }

    fun updateNotification(token: String){
        api.UpdateReadNotification(tokenUser = "Bearer $token").enqueue(object : Callback<ResponseUpdateNotif>{
            override fun onResponse(
                call: Call<ResponseUpdateNotif>,
                response: Response<ResponseUpdateNotif>
            ) {
                if(response.isSuccessful){
                    _responseUpdateNotif.postValue(response.body())
                }else{
                    _responseUpdateNotif.postValue(null)
                }
            }

            override fun onFailure(call: Call<ResponseUpdateNotif>, t: Throwable) {
                _responseUpdateNotif.postValue(null)
            }

        })
    }

}