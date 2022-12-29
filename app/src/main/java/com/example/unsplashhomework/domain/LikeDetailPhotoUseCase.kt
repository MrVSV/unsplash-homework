package com.example.unsplashhomework.domain

import com.example.unsplashhomework.domain.model.PhotoDetails

interface LikeDetailPhotoUseCase {

    suspend fun likeDetailPhoto(item: PhotoDetails)

}