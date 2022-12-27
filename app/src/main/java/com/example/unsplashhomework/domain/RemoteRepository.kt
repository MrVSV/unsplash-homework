package com.example.unsplashhomework.domain

import com.example.unsplashhomework.data.api.dto.PhotoDetailsDto
import com.example.unsplashhomework.data.api.dto.PhotoListDto
import com.example.unsplashhomework.data.api.dto.SearchDto
import com.example.unsplashhomework.data.api.dto.WrapperPhotoDto

interface RemoteRepository {

    suspend fun getPhotos(page: Int): PhotoListDto

    suspend fun getPhotoDetails(id: String): PhotoDetailsDto

    suspend fun likePhoto(id: String): WrapperPhotoDto

    suspend fun unlikePhoto(id: String): WrapperPhotoDto

    suspend fun searchPhotos(query:String, page: Int): SearchDto
}