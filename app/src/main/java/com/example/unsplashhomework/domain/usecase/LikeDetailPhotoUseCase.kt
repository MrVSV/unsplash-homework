package com.example.unsplashhomework.domain.usecase

import com.example.unsplashhomework.domain.model.PhotoDetails

interface LikeDetailPhotoUseCase {

    suspend fun likeDetailPhoto(item: PhotoDetails)

}