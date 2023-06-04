package com.damar.submitinter2.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.damar.submitinter2.paging.StoryRepository
import com.damar.submitinter2.response.Story

class HomeViewModel(storyRepository: StoryRepository) : ViewModel() {

    val story: LiveData<PagingData<Story>> =
        storyRepository.getStory()
            .cachedIn(viewModelScope)
}
