package com.example.unsplashhomework.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PhotosDao {

    @Query("SELECT photo, date FROM photos")
    fun getPhotos(): List<Photos>

    @Insert
    fun insert(photos: Photos)

    @Query("DELETE FROM photos")
    suspend fun deleteAll()
}