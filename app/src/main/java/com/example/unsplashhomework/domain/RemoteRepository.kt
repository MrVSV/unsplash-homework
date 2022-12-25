package com.example.unsplashhomework.domain

import com.example.unsplashhomework.data.remote.photodetailsmodel.PhotoDetails
import com.example.unsplashhomework.data.remote.photosmodel.PhotosModel
import com.example.unsplashhomework.data.remote.photosmodel.PhotosModelItem

interface RemoteRepository {

    suspend fun getPhotos(page: Int): PhotosModel

    suspend fun getPhotoDetails(id: String): PhotoDetails

    suspend fun likePhoto(id: String): PhotosModelItem

    suspend fun unlikePhoto(id: String): PhotosModelItem
}