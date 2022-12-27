package com.example.unsplashhomework.data.repository

import com.example.unsplashhomework.data.api.ApiPhotos
import com.example.unsplashhomework.data.api.dto.PhotoDetailsDto
import com.example.unsplashhomework.data.api.dto.PhotoListDto
import com.example.unsplashhomework.data.api.dto.SearchDto
import com.example.unsplashhomework.data.api.dto.WrapperPhotoDto
import com.example.unsplashhomework.domain.RemoteRepository
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(private val apiPhotos: ApiPhotos) :
    RemoteRepository {

    override suspend fun getPhotos(page: Int): PhotoListDto = apiPhotos.getPhotos(page)

    override suspend fun getPhotoDetails(id: String): PhotoDetailsDto =
        apiPhotos.getPhotoDetails(id)

    override suspend fun likePhoto(id: String): WrapperPhotoDto = apiPhotos.like(id)

    override suspend fun unlikePhoto(id: String): WrapperPhotoDto = apiPhotos.unlike(id)

    override suspend fun searchPhotos(query: String, page: Int): SearchDto =
        apiPhotos.searchPhotos(query, page)

}