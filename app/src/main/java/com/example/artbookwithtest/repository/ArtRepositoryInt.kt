package com.example.artbookwithtest.repository

import androidx.lifecycle.LiveData
import com.example.artbookwithtest.Model.Art
import com.example.artbookwithtest.Model.ImageResponse
import com.example.artbookwithtest.util.Resource

interface ArtRepositoryInt {

    suspend fun insertArt(art: Art)

    suspend fun deleteArt(art:Art)

    fun getArt() : LiveData<List<Art>>

    suspend fun seartchImage(imageString: String) : Resource<ImageResponse>
}