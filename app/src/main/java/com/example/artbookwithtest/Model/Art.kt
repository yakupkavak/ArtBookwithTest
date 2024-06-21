package com.example.artbookwithtest.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("arts")
data class Art(
    val name:String,
    val artistName: String,
    val year: Int,
    val imageUrl: String,
    @PrimaryKey(autoGenerate = true)
    val imageId: Int? = null
)