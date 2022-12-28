package com.example.unsplashhomework.domain

import com.example.unsplashhomework.data.model.PhotoDetails

interface LikeDetailPhotoUseCase {

    suspend fun likeDetailPhoto(item: PhotoDetails)

}