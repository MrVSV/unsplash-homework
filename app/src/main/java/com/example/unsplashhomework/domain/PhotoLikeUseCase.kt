package com.example.unsplashhomework.domain

import com.example.unsplashhomework.domain.model.Photo
import com.example.unsplashhomework.data.repository.PhotosPagingSourceRepository
import javax.inject.Inject

class PhotoLikeUseCase @Inject constructor(private val repository: PhotosPagingSourceRepository) {

    /** если прям доебываться это уже не юзкейс
     * юзкейс должен выполнять одно единственное действия...в нашем у тебя тут еще
     * и запись в базу данных
     * если пошел по юзкейсом выноси запись данных в отдельный юзкейз
     * Если просто цель показать что ты могешь ими пользоваться то можно создать парочку юзкейсов
     * а пользоваться репозиторием, это не запрещено
     * и да я тоже чутка покапался в вопросе юзкей не должен возвращать ни энтити ни дто*/

    suspend fun likePhoto(item: Photo) {
        val response = if (item.likedByUser) repository.deleteLike(item.id).photo
        else repository.setLike(item.id).photo
        val newItem =
            item.copy(likedByUser = response.likedByUser, likes = response.likes)
        repository.updateLikeDB(newItem.toPhotoEntity())
    }
}