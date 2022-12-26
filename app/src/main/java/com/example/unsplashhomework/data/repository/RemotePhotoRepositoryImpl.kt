package com.example.unsplashhomework.data.repository

import com.example.unsplashhomework.data.api.ApiPhotos
import com.example.unsplashhomework.data.api.photodto.PhotoDetailsDto
import com.example.unsplashhomework.data.api.photodto.PhotoListDto
import com.example.unsplashhomework.data.api.photodto.WrapperPhotoDto
import com.example.unsplashhomework.domain.RemotePhotoRepository
import javax.inject.Inject

class RemotePhotoRepositoryImpl @Inject constructor(private val apiPhotos: ApiPhotos): RemotePhotoRepository {

    override suspend fun getPhotos(page: Int): PhotoListDto = apiPhotos.getPhotos(page)

    override suspend fun getPhotoDetails(id: String): PhotoDetailsDto = apiPhotos.getPhotoDetails(id)

    override suspend fun likePhoto(id: String): WrapperPhotoDto = apiPhotos.like(id)

    override suspend fun unlikePhoto(id: String): WrapperPhotoDto = apiPhotos.unlike(id)

}