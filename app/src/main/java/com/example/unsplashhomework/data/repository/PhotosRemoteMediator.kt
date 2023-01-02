package com.example.unsplashhomework.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.unsplashhomework.data.local.entity.PhotoEntity
import com.example.unsplashhomework.data.state.Requester
import com.example.unsplashhomework.domain.repository.LocalRepository
import com.example.unsplashhomework.domain.repository.PhotoRemoteRepository
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PhotosRemoteMediator @Inject constructor(
    private val local: LocalRepository,
    private val remote: PhotoRemoteRepository,
    private val requester: Requester
) : RemoteMediator<Int, PhotoEntity>() {

    private var pageIndex = 0

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PhotoEntity>,
    ): MediatorResult {
        Log.d(TAG, "load: $requester $pageIndex")
        pageIndex = getIndex(loadType) ?: return MediatorResult.Success(true)

        return try {
            val response = remote.getPhotoList(requester, pageIndex).toListEntity()
            if (loadType == LoadType.REFRESH) local.refresh(response)
            else local.insertData(response)
            MediatorResult.Success(endOfPaginationReached = response.isEmpty())
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private fun getIndex(loadType: LoadType): Int? {
        Log.d(TAG, "getIndex: $loadType")
        return when (loadType) {
            LoadType.PREPEND -> null
            LoadType.REFRESH -> 0
            LoadType.APPEND -> null /*поменять на ++pageIndex чтобы грузить всё*/
        }
    }
}