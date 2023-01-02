package com.example.unsplashhomework.domain.usecase

import com.example.unsplashhomework.domain.model.Photo

interface PhotoLikeUseCase {

    suspend fun likePhoto(item: Photo)
}