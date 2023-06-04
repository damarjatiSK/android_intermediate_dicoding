package com.damar.submitinter2.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.damar.submitinter2.api.Injection
import com.damar.submitinter2.home.HomeViewModel

class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                HomeViewModel(Injection.provideRepository()) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
