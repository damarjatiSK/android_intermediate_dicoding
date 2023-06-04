package com.damar.submitinter2.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.damar.submitinter2.api.ApiService
import com.damar.submitinter2.home.DataDummy
import com.damar.submitinter2.home.getOrAwaitValue
import com.damar.submitinter2.login.LoginActivity
import com.damar.submitinter2.paging.StoryRepository
import com.damar.submitinter2.response.RegisterResponse
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class RegisterViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var apiService: ApiService

    private lateinit var registerViewModel: RegisterViewModel

    @Before
    fun setUp() {
        registerViewModel = RegisterViewModel()
    }

    @Test
    fun `when createUser with successful response, infoError should be false`() {
        //
    }

    @Test
    fun `when createUser with error response, infoError should be true and errorMessage should be set`() {
        //
    }
}



