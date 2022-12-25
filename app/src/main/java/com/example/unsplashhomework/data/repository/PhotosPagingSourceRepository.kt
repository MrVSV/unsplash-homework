package com.example.unsplashhomework.data.repository

import androidx.paging.*
import com.example.unsplashhomework.data.local.entity.PhotoEntity
import com.example.unsplashhomework.data.model.Photo
import com.example.unsplashhomework.domain.LocalRepository
import com.example.unsplashhomework.domain.RemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PhotosPagingSourceRepository(
    private val remote: RemoteRepository,
    private val local: LocalRepository
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getFlowPhoto(): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            remoteMediator = PhotosRemoteMediator(local, remote),
            pagingSourceFactory = { local.getPagingData() }
        ).flow.map {
            it.map { entity ->
                entity.toPhoto()
            }
        }
    }

    suspend fun setLick(id: String) = remote.likePhoto(id)

    suspend fun deleteLick(id: String) = remote.unlikePhoto(id)

    suspend fun updateLikeDB(entity: PhotoEntity) = local.setLickInDataBase(entity)
}