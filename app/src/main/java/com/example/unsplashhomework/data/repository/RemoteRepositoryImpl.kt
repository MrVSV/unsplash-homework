package com.example.unsplashhomework.data.repository

import com.example.unsplashhomework.data.api.ApiPhotos
import com.example.unsplashhomework.data.remote.photodetailsmodel.PhotoDetails
import com.example.unsplashhomework.data.remote.photosmodel.PhotosModel
import com.example.unsplashhomework.data.remote.photosmodel.PhotosModelItem
import com.example.unsplashhomework.domain.RemoteRepository
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(private val apiPhotos: ApiPhotos): RemoteRepository {

    override suspend fun getPhotos(page: Int): PhotosModel = apiPhotos.getPhotos(page)

    override suspend fun getPhotoDetails(id: String): PhotoDetails = apiPhotos.getPhotoDetails(id)

    override suspend fun likePhoto(id: String): PhotosModelItem = apiPhotos.like(id)

    override suspend fun unlikePhoto(id: String): PhotosModelItem = apiPhotos.unlike(id)

}