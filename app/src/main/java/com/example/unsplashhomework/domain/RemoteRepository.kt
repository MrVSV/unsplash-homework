package com.example.unsplashhomework.domain

import com.example.unsplashhomework.data.api.dto.PhotoDetailsDto
import com.example.unsplashhomework.data.api.dto.PhotoDto
import com.example.unsplashhomework.data.api.dto.PhotoListDto

interface RemoteRepository {

    suspend fun getPhotos(page: Int): PhotoListDto

    suspend fun getPhotoDetails(id: String): PhotoDetailsDto

    suspend fun likePhoto(id: String): PhotoDto

    suspend fun unlikePhoto(id: String): PhotoDto
}