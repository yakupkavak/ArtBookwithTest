package com.example.artbookwithtest.Roomdb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.example.artbookwithtest.Model.Art
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@SmallTest
@ExperimentalCoroutinesApi
class ArtDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: RoomDatabase
    private lateinit var dao: ArtDao

    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext()
            ,RoomDatabase::class.java).allowMainThreadQueries().build()
        dao= database.artDao()
    }

    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun insertArtTest() = runTest{
        val exampleArt = Art("Mona Lisa","Melis",100,"test.com",1)
        dao.insertArt(exampleArt)
        val arts = dao.observeArts().getOrAwaitValueTest()
        assertThat(arts).contains(exampleArt)
    }

    @Test
    fun deleteArtTest() = runTest{
        val exampleArt = Art("Mona Lisa","Melis",100,"test.com",1)
        dao.insertArt(exampleArt)
        dao.deleteArt(exampleArt)
        val arts = dao.observeArts().getOrAwaitValueTest()
        assertThat(arts).isEmpty()
        //ya da doesNotContain(exampleArt)
    }





}