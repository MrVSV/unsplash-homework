package com.example.unsplashhomework.presentation.photos.details

import androidx.lifecycle.viewModelScope
import com.example.unsplashhomework.data.model.Photo
import com.example.unsplashhomework.domain.PhotoLikeUseCase
import com.example.unsplashhomework.domain.PhotoRemoteRepository
import com.example.unsplashhomework.tools.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnePhotoDetailsViewModel @Inject constructor(
    private val repository: PhotoRemoteRepository,
    private val photoLikeUseCase: PhotoLikeUseCase
) : BaseViewModel() {

    /** лучше разделять стейт загрузки и данных */
    private val _state = MutableStateFlow<DetailsState>(DetailsState.NotStartedYet)
    val state = _state.asStateFlow()

    /** тогда и эта запись будет лучше */
    fun loadPhotoDetails(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val a = repository.getPhotoDetails(id = id)
                _state.value = DetailsState.Success(a)
            } catch (_: Exception) {
                _state.value = DetailsState.LoadingError
            }
        }
    }

    /** пока нечего говорить */
    fun like(item: Photo) {
        viewModelScope.launch(Dispatchers.IO + handler) {
            photoLikeUseCase.likePhoto(item)
//            Log.d(TAG, "old like: ${item.id} isLiked = ${item.likedByUser} likes = ${item.likes}")
//            Log.d(TAG, "new like: ${newItem.id} isLiked = ${newItem.likedByUser} likes = ${newItem.likes}")
        }
    }
}
