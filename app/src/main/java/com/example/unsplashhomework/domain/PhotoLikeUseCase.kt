package com.example.unsplashhomework.domain

import com.example.unsplashhomework.data.model.Photo
import com.example.unsplashhomework.data.repository.PhotosPagingSourceRepository
import javax.inject.Inject

class PhotoLikeUseCase @Inject constructor(private val repository: PhotosPagingSourceRepository) {

    suspend fun likePhoto(item: Photo) {
        val response = if (item.likedByUser) repository.deleteLike(item.id).photo
        else repository.setLike(item.id).photo
        val newItem =
            item.copy(likedByUser = response.likedByUser, likes = response.likes)
        repository.updateLikeDB(newItem.toPhotoEntity())
    }
}