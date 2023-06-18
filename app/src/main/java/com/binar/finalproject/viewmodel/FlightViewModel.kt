package com.binar.finalproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binar.finalproject.model.searchflight.Flight
import com.binar.finalproject.model.searchflight.PostSearchFlight
import com.binar.finalproject.model.searchflight.ResponseDataFlight
import com.binar.finalproject.model.searchflight.SearchFlight
import com.binar.finalproject.network.RestfulApi
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class FlightViewModel @Inject constructor(private val api : RestfulApi) : ViewModel() {

    private val _dataFlight = MutableLiveData<List<Flight>>()
    val dataFlight : LiveData<List<Flight>> = _dataFlight

    //sementara
    fun getDataFlight(data : PostSearchFlight){
        api.getSearchDataFlight(data).enqueue(object : Callback<ResponseDataFlight>{
            override fun onResponse(
                call: Call<ResponseDataFlight>,
                response: Response<ResponseDataFlight>
            ) {
                if(response.isSuccessful){
                    _dataFlight.postValue(response.body()!!.data.flight)
                }else{
                    _dataFlight.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<ResponseDataFlight>, t: Throwable) {
                _dataFlight.postValue(emptyList())
            }

        })
    }
}