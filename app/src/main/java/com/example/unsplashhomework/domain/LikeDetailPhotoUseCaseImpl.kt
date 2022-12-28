package com.example.unsplashhomework.domain

import com.example.unsplashhomework.data.model.PhotoDetails
import com.example.unsplashhomework.data.repository.PhotosPagingSourceRepository
import javax.inject.Inject

class LikeDetailPhotoUseCaseImpl @Inject constructor(private val repository: PhotosPagingSourceRepository) :
    LikeDetailPhotoUseCase {

    override suspend fun likeDetailPhoto(item: PhotoDetails) {
        if (item.likedByUser) repository.deleteLike(item.id).photo
        else repository.setLike(item.id).photo
    }
}