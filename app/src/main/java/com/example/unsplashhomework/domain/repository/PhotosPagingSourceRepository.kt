package com.example.unsplashhomework.domain.repository

import androidx.paging.PagingData
import com.example.unsplashhomework.data.api.photodto.WrapperPhotoDto
import com.example.unsplashhomework.data.local.entity.PhotoEntity
import com.example.unsplashhomework.data.state.Requester
import com.example.unsplashhomework.domain.model.Photo
import kotlinx.coroutines.flow.Flow

interface PhotosPagingSourceRepository {

    fun getFlowPhoto(requester: Requester): Flow<PagingData<Photo>>

    suspend fun setLike(id: String): WrapperPhotoDto

    suspend fun deleteLike(id: String): WrapperPhotoDto

    suspend fun updateLikeDB(entity: PhotoEntity)
}