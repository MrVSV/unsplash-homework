package com.example.unsplashhomework.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.unsplashhomework.data.local.entity.PhotoEntity
import com.example.unsplashhomework.domain.LocalRepository
import com.example.unsplashhomework.domain.RemoteRepository
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PhotosRemoteMediator @Inject constructor(
    private val local: LocalRepository,
    private val remote: RemoteRepository,
) : RemoteMediator<Int, PhotoEntity>() {

    private var pageIndex = 0

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PhotoEntity>,
    ): MediatorResult {

        pageIndex = getIndex(loadType) ?: return MediatorResult.Success(true)

        return try {
            val response = remote.getPhotos(pageIndex).toListEntity()
            if (loadType == LoadType.REFRESH) local.refresh(response)
            else local.insertData(response)
            MediatorResult.Success(endOfPaginationReached = response.isEmpty())
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private fun getIndex(loadType: LoadType): Int? {
        return when (loadType) {
            LoadType.PREPEND -> null
            LoadType.REFRESH -> 1
            LoadType.APPEND -> ++pageIndex
        }
    }
}