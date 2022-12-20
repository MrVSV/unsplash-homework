package com.example.unsplashhomework.data.remote


import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.unsplashhomework.data.remote.PhotosModel.PhotosModelItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PhotosPagingSource@Inject constructor(
    private val repository: RemoteRepository
) : PagingSource<Int, PhotosModelItem>() {

    override fun getRefreshKey(state: PagingState<Int, PhotosModelItem>): Int {
        Log.d("Kart", "getRefreshKey: ")
        return FIRST_PAGE
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotosModelItem> {
        val page = params.key ?: FIRST_PAGE
        Log.d("Kart", "load: $page")
        /** с (Dispatchers.IO)? **/
        return kotlin.runCatching {
            withContext(Dispatchers.IO){
            repository.getData(page)}
        }.fold(
            onSuccess = {
                Log.d("Kart", "load: грузит")
                LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey = if (it.isEmpty()) null else page + 1
                )

            },
            onFailure = {
                Log.d("Kart", "load: не грузит")
//                throwable.value = it
                LoadResult.Error(it)
            })
    }

    private companion object {
        private const val FIRST_PAGE = 1
    }
}