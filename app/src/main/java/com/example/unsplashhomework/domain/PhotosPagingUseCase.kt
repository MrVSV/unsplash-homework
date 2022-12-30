package com.example.unsplashhomework.domain

import android.util.Log
import androidx.paging.PagingData
import com.example.unsplashhomework.data.repository.PhotosPagingSourceRepository
import com.example.unsplashhomework.data.state.Requester
import com.example.unsplashhomework.domain.model.Photo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PhotosPagingUseCase @Inject constructor(
    private val photosRepository: PhotosPagingSourceRepository
) {
    fun getPhoto(query:String): Flow<PagingData<Photo>> {
        Log.d("Query", "getPhoto: $query")
        return photosRepository.getFlowPhoto(query, requester = Requester.FEED)
    }
}
