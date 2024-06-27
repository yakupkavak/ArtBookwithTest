package com.example.artbookwithtest.repository

import androidx.lifecycle.LiveData
import com.example.artbookwithtest.Model.Art
import com.example.artbookwithtest.Model.ImageResponse
import com.example.artbookwithtest.Roomdb.ArtDao
import com.example.artbookwithtest.api.RetrofitApi
import com.example.artbookwithtest.util.Resource
import javax.inject.Inject

class ArtRepository @Inject constructor(
    private val artDao: ArtDao,
    private val retrofitApi: RetrofitApi) : ArtRepositoryInt {
    override suspend fun insertArt(art: Art) {
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDao.deleteArt(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artDao.observeArts()
    }

    //BURADA ALDIGIMIZ VERİYİ RESOURCE OLARAK KULLANIYORUZ
    override suspend fun seartchImage(imageString: String): Resource<ImageResponse> {
        return try {
            val response = retrofitApi.imageSearch(imageString)
            if (response.isSuccessful){
                response.body()?.let { //boş değilse içine girdik
                    return@let Resource.success(it)  //yanıt boş dğeise bunu döndürdük
                } ?: Resource.error("There was an error",null) //yanıt boş ise bunu döndürdük
            }
            else{
                return Resource.error("There was an error",null)
            }

        }catch (e:Exception){
            return Resource.error("There was an error",null)
        }



     }
}