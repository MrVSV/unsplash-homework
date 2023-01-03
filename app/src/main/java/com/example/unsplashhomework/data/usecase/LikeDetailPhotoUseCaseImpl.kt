package com.example.unsplashhomework.data.usecase

import com.example.unsplashhomework.domain.model.PhotoDetails
import com.example.unsplashhomework.domain.repository.PhotosPagingSourceRepository
import com.example.unsplashhomework.domain.usecase.LikeDetailPhotoUseCase
import javax.inject.Inject

class LikeDetailPhotoUseCaseImpl @Inject constructor(private val repository: PhotosPagingSourceRepository) :
    LikeDetailPhotoUseCase {

    override suspend fun likeDetailPhoto(item: PhotoDetails) {
        if (item.likedByUser) repository.deleteLike(item.id).photo
        else repository.setLike(item.id).photo
    }
}