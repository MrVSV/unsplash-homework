package com.example.unsplashhomework.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Photos::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun photosDao(): PhotosDao
}