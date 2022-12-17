package com.example.unsplashhomework.data.remote

import com.example.unsplashhomework.data.local.PhotosDao
import com.example.unsplashhomework.data.remote.Api.Companion.retrofit
import javax.inject.Inject

class RemoteRepository@Inject constructor(private val photosDao: PhotosDao) {
    suspend fun getData(page: Int): PhotosModel {
        return retrofit.getPhotos(page)
    }
}