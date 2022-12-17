package com.example.unsplashhomework.data.remote


import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.unsplashhomework.data.remote.PhotosModel.PhotosModelItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PhotosPagingSource@Inject constructor(
    private var throwable: MutableLiveData<Throwable?>,
    private val repository: RemoteRepository
) : PagingSource<Int, PhotosModelItem>() {

    override fun getRefreshKey(state: PagingState<Int, PhotosModelItem>): Int {
        return FIRST_PAGE
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotosModelItem> {
        val page = params.key ?: FIRST_PAGE

        /** с (Dispatchers.IO)? **/
        return kotlin.runCatching {
            withContext(Dispatchers.IO){
            repository.getData(page)}
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey = if (it.isEmpty()) null else page + 1
                )
            },
            onFailure = {
                throwable.value = it
                LoadResult.Error(it)
            })
    }

    private companion object {
        private const val FIRST_PAGE = 1
    }
}