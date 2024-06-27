package com.example.artbookwithtest.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.artbookwithtest.Model.Art
import com.example.artbookwithtest.Model.ImageResponse
import com.example.artbookwithtest.util.Resource

class FakeArtRepository : ArtRepositoryInt {

    private val arts = mutableListOf<Art>()
    private val artsLivedata = MutableLiveData<List<Art>>(arts)

    override suspend fun insertArt(art: Art) {
        arts.add(art)
        refreshData()
    }

    override suspend fun deleteArt(art: Art) {
        arts.remove(art)
        refreshData()
    }

    override fun getArt(): LiveData<List<Art>> {
        return artsLivedata
    }

    override suspend fun seartchImage(imageString: String): Resource<ImageResponse> {
        return Resource.success(ImageResponse(listOf(),0,0))
    }

    private fun refreshData(){
        artsLivedata.postValue(arts)
    }
}