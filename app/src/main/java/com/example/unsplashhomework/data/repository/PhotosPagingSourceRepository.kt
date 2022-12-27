package com.example.unsplashhomework.data.repository

import android.content.ContentValues
import android.util.Log
import androidx.paging.*
import com.example.unsplashhomework.data.api.photodto.WrapperPhotoDto
import com.example.unsplashhomework.data.local.entity.PhotoEntity
import com.example.unsplashhomework.data.model.Photo
import com.example.unsplashhomework.domain.LocalRepository
import com.example.unsplashhomework.domain.PhotoRemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PhotosPagingSourceRepository @Inject constructor(
    private val remote: PhotoRemoteRepository,
    private val local: LocalRepository
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getFlowPhoto(query:String): Flow<PagingData<Photo>> {
        Log.d(ContentValues.TAG, "getFlowPhoto: ")
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            remoteMediator = PhotosRemoteMediator(local, remote, query),
            pagingSourceFactory = { local.getPagingData() }
        ).flow.map {
            it.map { entity ->
                entity.toPhoto()
            }
        }

    }

    suspend fun setLike(id: String): WrapperPhotoDto = remote.likePhoto(id)

    suspend fun deleteLike(id: String): WrapperPhotoDto = remote.unlikePhoto(id)

    suspend fun updateLikeDB(entity: PhotoEntity) = local.setLikeInDataBase(entity)
}