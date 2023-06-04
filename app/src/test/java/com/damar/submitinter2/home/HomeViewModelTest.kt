package com.damar.submitinter2.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.ListUpdateCallback
import com.damar.submitinter2.adapter.ListStoryAdapter
import com.damar.submitinter2.paging.StoryRepository
import com.damar.submitinter2.response.Story
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val ruleTest = RuleTest()

    @Mock
    private lateinit var storyRepository: StoryRepository

    private val dummyStoriesResponse = DataDummy.generateDummyStories()

    private val noopListUpdateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }

    @Test
    fun `getStories returns success`() = runBlockingTest {
        val expectedData = PagingData.from(dummyStoriesResponse.listStory)
        val expectedLiveData = MutableLiveData(expectedData)

        Mockito.`when`(storyRepository.getStory()).thenReturn(expectedLiveData)

        val homeViewModel = HomeViewModel(storyRepository)
        val actualData = homeViewModel.story.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = ListStoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )

        when (actualData) {
            is PagingData -> differ.submitData(actualData)
        }

        val actualSnapshot = differ.snapshot().items
        val expectedSnapshot = dummyStoriesResponse.listStory

        assertNotNull(actualSnapshot)
        assertEquals(expectedSnapshot, actualSnapshot)
        assertEquals(expectedSnapshot.size, actualSnapshot.size)
        assertEquals(expectedSnapshot[0], actualSnapshot[0])
    }

    @Test
    fun `getStories returns empty list`() = runBlockingTest {
        val expectedData = PagingData.from(emptyList<Story>())
        val expectedLiveData = MutableLiveData(expectedData)

        Mockito.`when`(storyRepository.getStory()).thenReturn(expectedLiveData)

        val listStoryViewModel = HomeViewModel(storyRepository)
        val actualData = listStoryViewModel.story.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = ListStoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )

        when (actualData) {
            is PagingData -> differ.submitData(actualData)
        }

        val actualSnapshot = differ.snapshot()
        val expectedSnapshot = emptyList<Story>()

        assertNotNull(actualSnapshot)
        assertEquals(expectedSnapshot, actualSnapshot)
        assertEquals(expectedSnapshot.size, actualSnapshot.size)
    }

}