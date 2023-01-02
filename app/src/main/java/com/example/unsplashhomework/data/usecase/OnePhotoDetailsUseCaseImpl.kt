package com.example.unsplashhomework.data.usecase

import com.example.unsplashhomework.domain.repository.PhotoRemoteRepository
import com.example.unsplashhomework.domain.usecase.OnePhotoDetailsUseCase
import javax.inject.Inject

class OnePhotoDetailsUseCaseImpl @Inject constructor(private val repository: PhotoRemoteRepository) :
    OnePhotoDetailsUseCase {

    override suspend fun getPhotoDetails(id: String) =
        repository.getPhotoDetails(id).toPhotoDetails()

}