package com.example.unsplashhomework.data.local

import androidx.room.*
import com.example.unsplashhomework.data.local.entity.PhotoEntity

@Dao
interface PhotosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(photoEntity: List<PhotoEntity>)

    @Query("SELECT * FROM photos")
    fun getPhotos(): List<PhotoEntity>

    @Query("DELETE FROM photos")
    suspend fun deleteAll()

    @Update
    suspend fun updateLocalLikes(photoEntity: PhotoEntity)


}