package com.example.unsplashhomework.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.unsplashhomework.data.remote.photosmodel.PhotosModelItem
import javax.inject.Inject

class PhotosPagingUseCase @Inject constructor(
    private val photosPagingSource: PhotosPagingSource
) {
    fun getPhoto(): Pager<Int, PhotosModelItem> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { photosPagingSource }
        )
    }
}
