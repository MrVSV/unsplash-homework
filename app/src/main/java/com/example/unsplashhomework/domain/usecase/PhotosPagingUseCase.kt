package com.example.unsplashhomework.domain.usecase

import androidx.paging.PagingData
import com.example.unsplashhomework.data.state.Requester
import com.example.unsplashhomework.domain.model.Photo
import kotlinx.coroutines.flow.Flow

interface PhotosPagingUseCase {

    fun getPhoto(requester: Requester) : Flow<PagingData<Photo>>

}
