package com.example.artbookwithtest.dependencyinjection

import android.content.Context
import androidx.room.Dao
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.example.artbookwithtest.R
import com.example.artbookwithtest.Roomdb.ArtDao
import com.example.artbookwithtest.Roomdb.RoomDatabase
import com.example.artbookwithtest.api.RetrofitApi
import com.example.artbookwithtest.repository.ArtRepository
import com.example.artbookwithtest.repository.ArtRepositoryInt
import com.example.artbookwithtest.util.Util.base_url
import com.google.gson.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRoom(@ApplicationContext context: Context) = Room.databaseBuilder(context,
        RoomDatabase::class.java,"ArtBookDb").build()

    @Singleton
    @Provides
    fun injectDao(database : RoomDatabase) = database.artDao()

    @Singleton
    @Provides
    fun injectRetrofitApi() : RetrofitApi {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(base_url)
            .build()
            .create(RetrofitApi::class.java)
    }

    @Singleton
    @Provides
    fun injectNormalRepository(dao: ArtDao,api : RetrofitApi) = ArtRepository(dao,api) as ArtRepositoryInt

    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) = Glide.with(context)
        .setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
        )

}