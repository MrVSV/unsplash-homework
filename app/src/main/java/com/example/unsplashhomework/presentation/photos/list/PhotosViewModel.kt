package com.example.unsplashhomework.presentation.photos.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.unsplashhomework.data.api.dto.PhotoDto
import com.example.unsplashhomework.domain.PhotoLikeUseCase
import com.example.unsplashhomework.domain.PhotosPagingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val photosPagingUseCase: PhotosPagingUseCase,
    private val photoLikeUseCase: PhotoLikeUseCase
) : ViewModel() {

    val a = MutableSharedFlow<PagingData<PhotoDto>>()

    suspend fun getPhoto() {
            photosPagingUseCase.getPhoto().flow.onEach {
                a.emit(it)
            }.launchIn(viewModelScope)
    }

    fun like(id: String, isLiked: Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            photoLikeUseCase.likePhoto(id, isLiked)
        }
    }
}