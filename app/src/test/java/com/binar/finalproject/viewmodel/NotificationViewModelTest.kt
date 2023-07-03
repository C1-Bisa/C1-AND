package com.binar.finalproject.viewmodel

import com.binar.finalproject.model.notification.responsegetnotif.ResponseDataNotification
import com.binar.finalproject.model.notification.updatenotif.ResponseUpdateNotif
import com.binar.finalproject.network.RestfulApi
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Call

class NotificationViewModelTest{
    private lateinit var api : RestfulApi

    @Before
    fun setup(){
        api = mockk()
    }

    @Test
    fun testRetriveNotification() : Unit = runBlocking {
        val responseRetrive = mockk<Call<ResponseDataNotification>>()

        //membuat objek palsu (mock) responseRetrive dari kelas <Call<ResponseDataFilm>>
        //Objek palsu ini akan digunakan sebagai respons palsu dari pemanggilan api.getAllFilmPopular().

        every {
            runBlocking {
                api.getNotification("asw")
            }
        } returns responseRetrive
        val result = api.getNotification("asw")
        //verify, kita memastikan bahwa metode api.getAllFilmPopular() benar-benar dipanggil dengan argumen yang sesuai.

        verify {
            runBlocking {
                api.getNotification("asw")
            }
        }
        assertEquals(result,responseRetrive)
    }

    @Test
    fun testRetriveUpdateNotification() : Unit = runBlocking {
        val responseRetrive = mockk<Call<ResponseUpdateNotif>>()

        //membuat objek palsu (mock) responseRetrive dari kelas <Call<ResponseDataFilm>>
        //Objek palsu ini akan digunakan sebagai respons palsu dari pemanggilan api.getAllFilmPopular().

        every {
            runBlocking {
                api.UpdateReadNotification("asw")
            }
        } returns responseRetrive
        val result = api.UpdateReadNotification("asw")
        //verify, kita memastikan bahwa metode api.getAllFilmPopular() benar-benar dipanggil dengan argumen yang sesuai.

        verify {
            runBlocking {
                api.UpdateReadNotification("asw")
            }
        }
        assertEquals(result,responseRetrive)
    }
}