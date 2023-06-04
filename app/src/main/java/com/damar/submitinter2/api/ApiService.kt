package com.damar.submitinter2.api

import com.damar.submitinter2.response.StoriesResponse
import com.damar.submitinter2.response.RegisterResponse
import com.damar.submitinter2.response.LoginResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    fun userRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("stories")
    suspend fun getAllStory(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): StoriesResponse

    @Multipart
    @POST("stories")
    fun addStory(
        @Header("Authorization") token: String,
        @Part("description") description: RequestBody,
        @Part photo: MultipartBody.Part
    ): Call<RegisterResponse>

    @GET("stories")
    fun getAllStoryForMap(
        @Header("Authorization") token: String,
        @Query("location") location: Int
    ): Call<StoriesResponse>
}