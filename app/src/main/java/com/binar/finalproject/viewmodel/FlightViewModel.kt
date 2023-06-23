package com.binar.finalproject.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binar.finalproject.model.getdetailflight.PostDataFlight
import com.binar.finalproject.model.getdetailflight.datadetailflight.Data
import com.binar.finalproject.model.getdetailflight.datadetailflight.ResponseDetailFlight
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

    private val _detailFlight = MutableLiveData<Data?>()
    val detailFlight : LiveData<Data?> = _detailFlight

    //sementara
    fun getDataFlight(data : PostSearchFlight, filterMap : Map<String, Boolean> = mapOf("toLower" to false)){
            api.getSearchDataFlight(data, filterMap).enqueue(object : Callback<ResponseDataFlight>{
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


    //get detail flight
    fun getDetailFlight(data : PostDataFlight){
        api.getDetailFlight(data).enqueue(object : Callback<ResponseDetailFlight>{
            override fun onResponse(
                call: Call<ResponseDetailFlight>,
                response: Response<ResponseDetailFlight>
            ) {
               if(response.isSuccessful){
                    _detailFlight.postValue(response.body()!!.data)
                   Log.i("HASIL_DETAIL_FLIGHT", response.body()!!.data.toString())
               }else{
                   Log.i("HASIL_DETAIL_FLIGHT", "FAIL")
                   _detailFlight.postValue(null)
               }
            }

            override fun onFailure(call: Call<ResponseDetailFlight>, t: Throwable) {
                _detailFlight.postValue(null)
                Log.i("HASIL_DETAIL_FLIGHT", "FAIL")

            }

        })
    }
}