package com.damar.submitinter2.response

import com.google.gson.annotations.SerializedName

data class Story(

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("photoUrl")
    val photoUrl: String? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("lat")
    val lat: Any? = null,

    @field:SerializedName("lon")
    val lon: Any? = null
)