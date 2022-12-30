package com.example.unsplashhomework.domain

import android.util.Log
import androidx.paging.PagingData
import com.example.unsplashhomework.data.repository.PhotosPagingSourceRepository
import com.example.unsplashhomework.data.state.Requester
import com.example.unsplashhomework.domain.model.Photo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchPhotoUseCase @Inject constructor(
    private val photosRepository: PhotosPagingSourceRepository
) {
    fun searchPhoto(query:String): Flow<PagingData<Photo>> {
        Log.d("Query", "searchPhoto: $query")
        return photosRepository.getFlowPhoto(Requester.SEARCH.query, requester = Requester.SEARCH)
    }
}