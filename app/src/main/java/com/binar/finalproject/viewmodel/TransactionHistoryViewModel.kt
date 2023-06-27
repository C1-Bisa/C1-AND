package com.binar.finalproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binar.finalproject.model.gethistory.Data
import com.binar.finalproject.model.gethistory.ResponseHistory
import com.binar.finalproject.model.gethistory.detailhistory.PostTransactionHistory
import com.binar.finalproject.model.gethistory.detailhistory.ResponseDetailTransactionHistory
import com.binar.finalproject.network.RestfulApi
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class TransactionHistoryViewModel @Inject constructor(private val api : RestfulApi) : ViewModel(){

    private val _detailHistory = MutableLiveData<com.binar.finalproject.model.gethistory.detailhistory.Data?>()
    val detailHistory : LiveData<com.binar.finalproject.model.gethistory.detailhistory.Data?> = _detailHistory

    private val _historyTransaction = MutableLiveData<List<Data>?>()
    val historyTransaction : LiveData<List<Data>?> = _historyTransaction


    fun getHistoryTransaction(token : String){
        api.getHistoryTransactionUser(tokenUser = "Bearer $token").enqueue(object : Callback<ResponseHistory>{
            override fun onResponse(
                call: Call<ResponseHistory>,
                response: Response<ResponseHistory>
            ) {
                if (response.isSuccessful){
                    _historyTransaction.postValue(response.body()!!.data)
                }else{
                    _historyTransaction.postValue(null)

                }
            }

            override fun onFailure(call: Call<ResponseHistory>, t: Throwable) {
                _historyTransaction.postValue(null)
            }

        })
    }
//    fun detailHistoryTransaction(data : PostTransactionHistory){
//        api.getDetailTransactionHistory(data).enqueue(object : Callback<ResponseDetailTransactionHistory>{
//            override fun onResponse(
//                call: Call<ResponseDetailTransactionHistory>,
//                response: Response<ResponseDetailTransactionHistory>
//            ) {
//                if (response.isSuccessful){
//                    _detailHistory.postValue(response.body()!!.data)
//                }else{
//                    _detailHistory.postValue(null)
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseDetailTransactionHistory>, t: Throwable) {
//                _detailHistory.postValue(null)
//
//            }
//
//        })
//    }


}