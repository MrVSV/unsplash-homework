package com.example.unsplashhomework.data.remote

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PhotosPagingUseCase @Inject constructor(
    private val photosPagingSource: PhotosPagingSource
) {
    fun getPhoto(): Pager<Int, PhotosModel.PhotosModelItem> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { photosPagingSource }
        )
    }
}
