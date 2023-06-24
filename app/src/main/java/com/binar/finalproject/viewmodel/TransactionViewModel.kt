package com.binar.finalproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binar.finalproject.model.searchflight.Flight
import com.binar.finalproject.model.transaction.request.RequestTransaction
import com.binar.finalproject.model.transaction.response.DataTransaction
import com.binar.finalproject.model.transaction.response.ResponseDataTransaction
import com.binar.finalproject.network.RestfulApi
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(private val api: RestfulApi) : ViewModel(){

    //post transaction
    private val _responDataTransaction = MutableLiveData<DataTransaction?>()
    val responDataTransaction : LiveData<DataTransaction?> = _responDataTransaction

    //gethistory transaction


    fun createTransaction(data : RequestTransaction, token : String){
        api.createTransaction(tokenUser = "Bearer $token", data).enqueue(object : Callback<ResponseDataTransaction>{
            override fun onResponse(
                call: Call<ResponseDataTransaction>,
                response: Response<ResponseDataTransaction>
            ) {
                if(response.isSuccessful){
                    _responDataTransaction.postValue(response.body()!!.dataTransaction)
                }else{
                    _responDataTransaction.postValue(null)
                }
            }

            override fun onFailure(call: Call<ResponseDataTransaction>, t: Throwable) {
                _responDataTransaction.postValue(null)
            }

        })
    }

}