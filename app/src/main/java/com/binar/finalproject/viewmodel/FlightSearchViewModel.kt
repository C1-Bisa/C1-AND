package com.binar.finalproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binar.finalproject.model.searchflight.SearchFlight
import com.binar.finalproject.model.user.ResponRegister
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class FlightSearchViewModel : ViewModel() {

    private val _from = MutableLiveData<String>()
    val from : LiveData<String> = _from

    private val _to = MutableLiveData<String>()
    val to : LiveData<String> = _to

    private val _dateDeparture = MutableLiveData<String>()
    val dateDeparture : LiveData<String> = _dateDeparture

    private val _dateReturn = MutableLiveData<String>()
    val dateReturn : LiveData<String> = _dateReturn

    private val _countPassenger = MutableLiveData<String>()
    val countPassenger : LiveData<String> = _countPassenger

    private val _seatClass = MutableLiveData<String>()
    val seatClass : LiveData<String> = _seatClass

    private val _dataSearch = MutableLiveData<SearchFlight?>()
    val dataSearch : LiveData<SearchFlight?> get() = _dataSearch

    //menyimpan waktu pick date
    private val _departureTime = MutableLiveData<String>()
    val departureTime : LiveData<String> = _departureTime


    // data search
    private val _searchFrom = MutableLiveData<String>()
    val searchFrom : LiveData<String> = _searchFrom

    private val _searchTo = MutableLiveData<String>()
    val searchTo : LiveData<String> = _searchTo

    private val _searchDateDeparture = MutableLiveData<String>()
    val searchDateDeparture : LiveData<String> = _searchDateDeparture

    private val _searchDateReturn = MutableLiveData<String>()
    val searchDateReturn : LiveData<String> = _searchDateReturn

    //menyimpan data jumlah penumpang sesuai usianya
    private val _dataPassenger = MutableLiveData<MutableList<Int>>()
    val dataPassenger : LiveData<MutableList<Int>> get()= _dataPassenger

    init {
        _dateDeparture.postValue(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("id", "ID"))))
        _dateReturn.postValue(LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("id", "ID"))))
        _dataPassenger.value = mutableListOf(1,0,0)
    }

    fun setFrom(data: String){
        _from.postValue(data)
    }

    fun setTo(data: String){
        _to.postValue(data)
    }

    fun setDateDeparture(date: String){
        _dateDeparture.postValue(date)
    }

    fun setDateReturn(date: String){
        _dateReturn.postValue(date)
    }

    fun setCountPassenger(count: String){
        _countPassenger.postValue(count)
    }

    fun setSeatClass(seat: String){
        _seatClass.postValue(seat)
    }

    fun setDepartureTime(time :String){
        _departureTime.postValue(time)
    }

    fun setSearchFrom(data : String){
        _searchFrom.postValue(data)
    }

    fun setSearchTo(data: String){
        _searchTo.postValue(data)
    }

    fun setSearchDateDeparture(date: String){
        _searchDateDeparture.postValue(date)
    }

    fun setSearchReturnDate(date: String){
        _searchDateReturn.postValue(date)
    }

    fun setDataPassenger(index: Int,num : Int){
        _dataPassenger.value?.apply {
            if (index in indices) {
                set(index, num)
//                _dataPassenger.value = this
            }
        }
    }


}