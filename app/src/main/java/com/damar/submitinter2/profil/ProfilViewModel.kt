package com.damar.submitinter2.profil

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.damar.submitinter2.response.LoginResult

class ProfilViewModel : ViewModel() {

    private val _user = MutableLiveData<LoginResult>()
    val user: LiveData<LoginResult> get() = _user

    fun setUser(userName: LoginResult) {
        _user.value = userName
    }
}
