package com.example.unsplashhomework.data.usecase

import com.example.unsplashhomework.data.repository.PhotosPagingSourceRepository
import com.example.unsplashhomework.data.state.Requester
import com.example.unsplashhomework.domain.usecase.PhotosPagingUseCase
import javax.inject.Inject

class PhotosPagingUseCaseImpl @Inject constructor(
    private val photosRepository: PhotosPagingSourceRepository
): PhotosPagingUseCase {
    override fun getPhoto(requester: Requester)=
        photosRepository.getFlowPhoto(requester)

}
