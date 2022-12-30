package com.example.unsplashhomework.presentation.photos.list

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.unsplashhomework.data.state.LoadState
import com.example.unsplashhomework.domain.PhotoLikeUseCase
import com.example.unsplashhomework.domain.PhotosPagingUseCase
import com.example.unsplashhomework.domain.SearchPhotoUseCase
import com.example.unsplashhomework.domain.model.Photo
import com.example.unsplashhomework.tools.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val photosPagingUseCase: PhotosPagingUseCase,
    private val photoLikeUseCase: PhotoLikeUseCase,
    private val searchPhotoUseCase: SearchPhotoUseCase
) : BaseViewModel() {

    private val query = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getPhoto(): Flow<PagingData<Photo>> {
        return if (query.value == "") {
            Log.d(TAG, "getPhoto: $query")
            query.asStateFlow()
                .flatMapLatest { photosPagingUseCase.getPhoto(it) }
                .cachedIn(viewModelScope)
        } else {
            query.asStateFlow()
                .flatMapLatest { searchPhotoUseCase.searchPhoto(it) }
                .cachedIn(viewModelScope)
        }
    }

    fun like(item: Photo) {
        viewModelScope.launch(Dispatchers.IO + handler) {
            photoLikeUseCase.likePhoto(item)
            _loadState.value = LoadState.SUCCESS
        }
    }

    private var job: Job? = null
    fun setQuery(newText: String, refresh: () -> Unit) {
        job?.cancel()
        job = viewModelScope.launch() {
            delay(500)
            query.value = newText
            refresh()
        }
    }
}