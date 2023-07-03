package com.binar.finalproject.viewmodel

import com.binar.finalproject.model.payment.RequestTransactionCode
import com.binar.finalproject.model.payment.ResponsePayment
import com.binar.finalproject.model.printticket.RequestBodyPrintTicket
import com.binar.finalproject.model.printticket.ResponsePrintTicket
import com.binar.finalproject.model.transaction.request.RequestTransaction
import com.binar.finalproject.model.transaction.response.ResponseDataTransaction
import com.binar.finalproject.model.transactionhistoryperid.request.RequestTransactionId
import com.binar.finalproject.model.transactionhistoryperid.response.ResponseTransactionPerId
import com.binar.finalproject.network.RestfulApi
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Call

class TransactionViewModelTest{
    private lateinit var api : RestfulApi

    @Before
    fun setup(){
        api = mockk()
    }
    @Test
    fun testRetriveCreateTransaction() : Unit = runBlocking {
        val responseRetrive = mockk<Call<ResponseDataTransaction>>()

        //membuat objek palsu (mock) responseRetrive dari kelas <Call<ResponseDataFilm>>
        //Objek palsu ini akan digunakan sebagai respons palsu dari pemanggilan api.getAllFilmPopular().

        every {
            runBlocking {
                api.createTransaction("asd", RequestTransaction(1, listOf(), listOf()))
            }
        } returns responseRetrive
        val result = api.createTransaction("asd", RequestTransaction(1, listOf(), listOf()))
        //verify, kita memastikan bahwa metode api.getAllFilmPopular() benar-benar dipanggil dengan argumen yang sesuai.

        verify {
            runBlocking {
                api.createTransaction("asd", RequestTransaction(1, listOf(), listOf()))
            }
        }
        assertEquals(result,responseRetrive)
    }

    @Test
    fun testRetrivePayment() : Unit = runBlocking {
        val responseRetrive = mockk<Call<ResponsePayment>>()

        //membuat objek palsu (mock) responseRetrive dari kelas <Call<ResponseDataFilm>>
        //Objek palsu ini akan digunakan sebagai respons palsu dari pemanggilan api.getAllFilmPopular().

        every {
            runBlocking {
                api.paymentTicketFlight("asd", RequestTransactionCode("aq"))
            }
        } returns responseRetrive
        val result =   api.paymentTicketFlight("asd", RequestTransactionCode("aq"))
        //verify, kita memastikan bahwa metode api.getAllFilmPopular() benar-benar dipanggil dengan argumen yang sesuai.

        verify {
            runBlocking {
                api.paymentTicketFlight("asd", RequestTransactionCode("aq"))
            }
        }
        assertEquals(result,responseRetrive)
    }

    @Test
    fun testRetriveTransactionById() : Unit = runBlocking {
        val responseRetrive = mockk<Call<ResponseTransactionPerId>>()

        //membuat objek palsu (mock) responseRetrive dari kelas <Call<ResponseDataFilm>>
        //Objek palsu ini akan digunakan sebagai respons palsu dari pemanggilan api.getAllFilmPopular().

        every {
            runBlocking {
                api.getDataTrasactionById("asd", RequestTransactionId(1))
            }
        } returns responseRetrive
        val result =   api.getDataTrasactionById("asd", RequestTransactionId(1))
        //verify, kita memastikan bahwa metode api.getAllFilmPopular() benar-benar dipanggil dengan argumen yang sesuai.

        verify {
            runBlocking {
                api.getDataTrasactionById("asd", RequestTransactionId(1))
            }
        }
        assertEquals(result,responseRetrive)
    }

    @Test
    fun testRetrivePrintTicket() : Unit = runBlocking {
        val responseRetrive = mockk<Call<ResponsePrintTicket>>()

        //membuat objek palsu (mock) responseRetrive dari kelas <Call<ResponseDataFilm>>
        //Objek palsu ini akan digunakan sebagai respons palsu dari pemanggilan api.getAllFilmPopular().

        every {
            runBlocking {
                api.printTicket("asd", RequestBodyPrintTicket("123"))
            }
        } returns responseRetrive
        val result =    api.printTicket("asd", RequestBodyPrintTicket("123"))
        //verify, kita memastikan bahwa metode api.getAllFilmPopular() benar-benar dipanggil dengan argumen yang sesuai.

        verify {
            runBlocking {
                api.printTicket("asd", RequestBodyPrintTicket("123"))
            }
        }
        assertEquals(result,responseRetrive)
    }
}