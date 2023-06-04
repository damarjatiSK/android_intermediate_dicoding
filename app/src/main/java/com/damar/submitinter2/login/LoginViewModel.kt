package com.damar.submitinter2.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.damar.submitinter2.api.ApiConfig
import com.damar.submitinter2.response.LoginResponse
import com.damar.submitinter2.response.LoginResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {

    private val _infoError = MutableLiveData<Boolean>()
    val infoError: LiveData<Boolean> = _infoError

    private val _userInfo = MutableLiveData<LoginResult>()
    val userInfo: LiveData<LoginResult> = _userInfo

    fun checkLogin(email: String, password: String) {
        val client = ApiConfig.getApiService().userLogin(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                _infoError.value = response.isSuccessful.not()
                _userInfo.value = response.body()?.loginResult
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("MainActivity", "onFailure: ${t.message}")
            }
        })
    }
}
