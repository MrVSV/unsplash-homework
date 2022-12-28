package com.example.unsplashhomework.domain

import javax.inject.Inject

class OnePhotoDetailsUseCaseImpl @Inject constructor(private val repository: PhotoRemoteRepository) :
    OnePhotoDetailsUseCase {

    override suspend fun getPhotoDetails(id: String) =
        repository.getPhotoDetails(id).toPhotoDetails()

}