package com.example.unsplashhomework.domain

import com.example.unsplashhomework.data.repository.PhotosPagingSourceRepository
import com.example.unsplashhomework.data.state.Requester
import javax.inject.Inject

class PhotosPagingUseCase @Inject constructor(
    private val photosRepository: PhotosPagingSourceRepository
) {
    fun getPhoto(query:String) = photosRepository.getFlowPhoto(query, requester = Requester.FEED)
}
