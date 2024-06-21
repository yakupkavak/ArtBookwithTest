package com.example.artbookwithtest.api

import com.example.artbookwithtest.Model.ImageResponse
import com.example.artbookwithtest.util.Util.api_key
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi  {

    @GET("/api/")
    suspend fun imageSearch(
        @Query("q") searchQuery: String,
        @Query("key") apiKey: String = api_key
    ):
            Response<ImageResponse>
}