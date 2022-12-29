package com.example.unsplashhomework.presentation.photos.details

import androidx.lifecycle.viewModelScope
import com.example.unsplashhomework.domain.LikeDetailPhotoUseCaseImpl
import com.example.unsplashhomework.domain.OnePhotoDetailsUseCaseImpl
import com.example.unsplashhomework.domain.model.PhotoDetails
import com.example.unsplashhomework.tools.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//TODO:здесь должна быть зависимость на интерфейсы юзкейсов, переписать,
// когда разберемся, где это все провайдить, без провайда не билдится
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
                val a = onePhotoDetailsUseCase.getPhotoDetails(id = item.id)
                _state.value = DetailsState.Success(a)

        }
    }
}
