package com.damar.submitinter2.home

import com.damar.submitinter2.response.StoriesResponse
import com.damar.submitinter2.response.Story

object DataDummy {

    fun generateDummyStories(): StoriesResponse {
        val listStory = ArrayList<Story>()

        repeat(10) { i ->
            val story = Story(
                createdAt = "2023-05-09T03:35:02Z",
                description = "This is description ${i + 1}",
                id = "id_${i + 1}",
                lat = (i + 1).toDouble() * 5,
                lon = (i + 1).toDouble() * 5,
                name = "Name ${i + 1}",
                photoUrl = "https://img.okezone.com/content/2020/08/21/320/2265466/eksistensi-logo-ayam-di-dunia-bisnis-dari-restoran-hingga-jamu-97G9P2QKv1.png"
            )
            listStory.add(story)
        }

        return StoriesResponse(
            error = false,
            message = "Successfully retrieved the story",
            listStory = listStory
        )
    }
}
