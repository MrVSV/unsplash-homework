package com.example.unsplashhomework.domain

import com.example.unsplashhomework.data.repository.PhotosPagingSourceRepository
import javax.inject.Inject

class PhotosPagingUseCase @Inject constructor(
    private val photosRepository: PhotosPagingSourceRepository
) {
    fun getPhoto() = photosRepository.getFlowPhoto()
}
