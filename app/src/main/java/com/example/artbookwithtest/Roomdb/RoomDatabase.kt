package com.example.artbookwithtest.Roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.artbookwithtest.Model.Art

@Database(entities = [Art::class], version = 1)
abstract class RoomDatabase: RoomDatabase() {
    abstract fun artDao() : ArtDao
}