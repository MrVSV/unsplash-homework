package com.example.unsplashhomework.domain

import com.example.unsplashhomework.data.repository.PhotosPagingSourceRepository
import com.example.unsplashhomework.data.state.Requester
import javax.inject.Inject

class SearchPhotoUseCase @Inject constructor(
    private val photosRepository: PhotosPagingSourceRepository
) {
    fun searchPhoto(query:String) = photosRepository.getFlowPhoto(query, requester = Requester.SEARCH)
}