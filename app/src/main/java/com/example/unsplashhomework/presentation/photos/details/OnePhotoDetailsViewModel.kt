package com.example.unsplashhomework.presentation.photos.details

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.unsplashhomework.data.model.PhotoDetails
import com.example.unsplashhomework.domain.*
import com.example.unsplashhomework.tools.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnePhotoDetailsViewModel @Inject constructor(
    private val onePhotoDetailsUseCase: OnePhotoDetailsUseCaseImpl,
    private val likeDetailPhotoUseCase: LikeDetailPhotoUseCaseImpl
) : BaseViewModel() {

    private val _state = MutableStateFlow<DetailsState>(DetailsState.NotStartedYet)
    val state = _state.asStateFlow()

    fun loadPhotoDetails(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val a = onePhotoDetailsUseCase.getPhotoDetails(id = id)
                _state.value = DetailsState.Success(a)
            } catch (_: Exception) {
                _state.value = DetailsState.LoadingError
            }
        }
    }

    fun like(item: PhotoDetails) {
        viewModelScope.launch(Dispatchers.IO + handler) {
            likeDetailPhotoUseCase.likeDetailPhoto(item)
            Log.d(TAG, "old like: ${item.id} isLiked = ${item.likedByUser} likes = ${item.likes}")
//            Log.d(TAG, "new like: ${newItem.id} isLiked = ${newItem.likedByUser} likes = ${newItem.likes}")
        }
    }
}
