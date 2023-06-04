package com.damar.submitinter2.api

import com.damar.submitinter2.home.HomeFragment
import com.damar.submitinter2.paging.StoryRepository

object Injection {
    fun provideRepository(): StoryRepository {
        val apiService = ApiConfig.getApiService()
        val userToken = HomeFragment.USER_TOKEN ?: ""
        return StoryRepository(apiService, userToken)
    }
}