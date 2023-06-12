package com.binar.finalproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binar.finalproject.model.airport.Airport
import com.binar.finalproject.model.airport.ResponseDataAirport
import com.binar.finalproject.network.RestfulApi
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AirportViewModel @Inject constructor(private val api : RestfulApi)  : ViewModel() {

    private val _dataAirport = MutableLiveData<List<Airport>>()
    val dataAirport : LiveData<List<Airport>> = _dataAirport

    fun getDataAirport(){
        api.getAllDataAirport().enqueue(object : Callback<ResponseDataAirport>{
            override fun onResponse(
                call: Call<ResponseDataAirport>,
                response: Response<ResponseDataAirport>
            ) {
                if(response.isSuccessful){
                    _dataAirport.postValue(response.body()!!.dataAirport.airport)
                }else{
                    _dataAirport.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<ResponseDataAirport>, t: Throwable) {
                _dataAirport.postValue(emptyList())
            }

        })
    }
}