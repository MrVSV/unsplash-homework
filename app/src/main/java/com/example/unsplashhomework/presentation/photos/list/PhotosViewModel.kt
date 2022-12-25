package com.example.unsplashhomework.presentation.photos.list

import androidx.lifecycle.viewModelScope
import com.example.unsplashhomework.data.model.Photo
import com.example.unsplashhomework.domain.PhotoLikeUseCase
import com.example.unsplashhomework.domain.PhotosPagingUseCase
import com.example.unsplashhomework.tools.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val photosPagingUseCase: PhotosPagingUseCase,
    private val photoLikeUseCase: PhotoLikeUseCase
) : BaseViewModel() {

//    val a = MutableSharedFlow<PagingData<PhotoDto>>()

    fun getPhoto() = photosPagingUseCase.getPhoto()


    fun like(item: Photo) {
        viewModelScope.launch(Dispatchers.IO + handler) {
            photoLikeUseCase.likePhoto(item)
//            Log.d(TAG, "old like: ${item.id} isLiked = ${item.likedByUser} likes = ${item.likes}")
//            Log.d(TAG, "new like: ${newItem.id} isLiked = ${newItem.likedByUser} likes = ${newItem.likes}")
        }
    }
}