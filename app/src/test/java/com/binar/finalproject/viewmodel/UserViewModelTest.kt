package com.binar.finalproject.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.binar.finalproject.model.user.PostRegister
import com.binar.finalproject.model.user.ResponRegister
import com.binar.finalproject.model.user.login.ResponseLogin
import com.binar.finalproject.network.RestfulApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class UserViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var apiUser: RestfulApi
    private lateinit var viewModel: UserViewModel
    private lateinit var responseObserver: Observer<ResponseLogin?>



    @Before
    fun setup() {
        apiUser = mock()
        viewModel = UserViewModel(apiUser)
        responseObserver = mock()
        viewModel.responseLogin.observeForever(responseObserver)

    }


    @Test
    fun postRegist_withErrorResponse_updatesResponseLoginToNull() {
        val mockResponse: Response<ResponRegister> = mock()
        whenever(mockResponse.isSuccessful).thenReturn(false)

        val mockData: PostRegister = mock()
        val mockCall: Call<ResponRegister> = mock()
        whenever(apiUser.postRegistUser(mockData)).thenReturn(mockCall)
        whenever(mockCall.enqueue(any())).thenAnswer { invocation ->
            val callback = invocation.arguments[0] as Callback<ResponRegister>
            callback.onResponse(mockCall, mockResponse)
        }

        viewModel.postRegist(mockData)

        verify(responseObserver, never()).onChanged(any())
        assertEquals(null, viewModel.responseLogin.value)
    }




}