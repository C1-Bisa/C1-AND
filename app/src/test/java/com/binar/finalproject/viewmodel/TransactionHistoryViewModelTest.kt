package com.binar.finalproject.viewmodel

import com.binar.finalproject.model.gethistory.ResponseHistory
import com.binar.finalproject.network.RestfulApi
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Call

class TransactionHistoryViewModelTest{
    private lateinit var api : RestfulApi

    @Before
    fun setup(){
        api = mockk()
    }
    @Test
    fun testRetriveDataHistory() : Unit = runBlocking {
        val responseRetrive = mockk<Call<ResponseHistory>>()

        //membuat objek palsu (mock) responseRetrive dari kelas <Call<ResponseDataFilm>>
        //Objek palsu ini akan digunakan sebagai respons palsu dari pemanggilan api.getAllFilmPopular().

        every {
            runBlocking {
                api.getHistoryTransactionUser("asd")
            }
        } returns responseRetrive
        val result = api.getHistoryTransactionUser("asd")
        //verify, kita memastikan bahwa metode api.getAllFilmPopular() benar-benar dipanggil dengan argumen yang sesuai.

        verify {
            runBlocking {
                api.getHistoryTransactionUser("asd")
            }
        }
        assertEquals(result,responseRetrive)
    }
}