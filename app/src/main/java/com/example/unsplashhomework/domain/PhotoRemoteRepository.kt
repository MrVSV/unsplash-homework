package com.example.unsplashhomework.domain

import com.example.unsplashhomework.data.api.photodto.PhotoDetailsDto
import com.example.unsplashhomework.data.api.photodto.PhotoListDto
import com.example.unsplashhomework.data.api.photodto.WrapperPhotoDto

interface PhotoRemoteRepository {

    suspend fun getPhotos(page: Int): PhotoListDto

    suspend fun getPhotoDetails(id: String): PhotoDetailsDto

    suspend fun likePhoto(id: String): WrapperPhotoDto

    suspend fun unlikePhoto(id: String): WrapperPhotoDto
}