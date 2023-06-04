package com.damar.submitinter2.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.damar.submitinter2.api.ApiConfig
import com.damar.submitinter2.response.StoriesResponse
import com.damar.submitinter2.response.Story
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapViewModel : ViewModel() {
    private val _isErrorInfo = MutableLiveData<Boolean>()

    private val _storyInfo = MutableLiveData<List<Story?>>()
    val storyInfo: LiveData<List<Story?>> = _storyInfo

    fun getAllStory(token: String) {
        val client = ApiConfig.getApiService().getAllStoryForMap(token, 1)
        client.enqueue(object : Callback<StoriesResponse> {
            override fun onResponse(
                call: Call<StoriesResponse>,
                response: Response<StoriesResponse>
            ) {
                val isSuccess = response.isSuccessful
                _isErrorInfo.value = isSuccess
                _storyInfo.value = response.body()?.listStory.takeIf { isSuccess }
            }

            override fun onFailure(call: Call<StoriesResponse>, t: Throwable) {
                Log.e("MapViewModel", "onFailure: ${t.message}")
            }
        })
    }
}


