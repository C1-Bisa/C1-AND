package com.binar.finalproject.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binar.finalproject.model.payment.RequestTransactionCode
import com.binar.finalproject.model.payment.ResponsePayment
import com.binar.finalproject.model.transaction.request.RequestTransaction
import com.binar.finalproject.model.transaction.response.DataTransaction
import com.binar.finalproject.model.transaction.response.ResponseDataTransaction
import com.binar.finalproject.model.transactionhistoryperid.request.RequestTransactionId
import com.binar.finalproject.model.transactionhistoryperid.response.Data
import com.binar.finalproject.model.transactionhistoryperid.response.ResponseTransactionPerId
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

    //post payment
    private val _responsePayment = MutableLiveData<ResponsePayment?>()
    val responsePayment : LiveData<ResponsePayment?> = _responsePayment

    //get transaction by id
    private val _responseTransactionById = MutableLiveData<Data?>()
    val responseTransactionById : LiveData<Data?> = _responseTransactionById


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

    fun postPayment(data : RequestTransactionCode, token : String){
        api.paymentTicketFlight(tokenUser = "Bearer $token", data).enqueue(object : Callback<ResponsePayment>{
            override fun onResponse(
                call: Call<ResponsePayment>,
                response: Response<ResponsePayment>
            ) {
                if(response.isSuccessful){
                    _responsePayment.postValue(response.body())
                }else{
                    _responsePayment.postValue(null)
                }
            }

            override fun onFailure(call: Call<ResponsePayment>, t: Throwable) {
                _responsePayment.postValue(null)
            }

        })
    }

    fun getTransactionById(token: String, data : RequestTransactionId){
        api.getDataTrasactionById(tokenUser = "Bearer $token", data).enqueue(object : Callback<ResponseTransactionPerId>{
            override fun onResponse(
                call: Call<ResponseTransactionPerId>,
                response: Response<ResponseTransactionPerId>
            ) {
                if(response.isSuccessful){
                    _responseTransactionById.postValue(response.body()!!.data)
                    Log.d("DATA_RESPON_TRANS_ID", response.body().toString())
                }else{
                    _responseTransactionById.postValue(null)
                    Log.d("DATA_RESPON_TRANS_ID", "fail")
                }
            }

            override fun onFailure(call: Call<ResponseTransactionPerId>, t: Throwable) {
                _responseTransactionById.postValue(null)
                Log.d("DATA_RESPON_TRANS_ID", "fail")
            }

        })
    }

}