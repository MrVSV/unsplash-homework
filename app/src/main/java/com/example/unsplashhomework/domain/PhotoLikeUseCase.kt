package com.example.unsplashhomework.domain

import javax.inject.Inject

class PhotoLikeUseCase @Inject constructor(private val repository: RemoteRepository) {

    suspend fun likePhoto(id: String, isLiked: Boolean) {
        if (isLiked) repository.unlikePhoto(id)
        else repository.likePhoto(id)
    }
}