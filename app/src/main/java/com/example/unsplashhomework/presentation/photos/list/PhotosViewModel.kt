package com.example.unsplashhomework.presentation.photos.list

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.unsplashhomework.data.state.LoadState
import com.example.unsplashhomework.data.state.Requester
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
    /**у тебя вью модель не будет работь с этим юзкейсом на прямую
     * зачем он тут> */
    private val searchPhotoUseCase: SearchPhotoUseCase,
) : BaseViewModel() {

    private val query = MutableStateFlow("")
    /** это переменная она поидеи должнать всегда в верху
     * мне так просто проще было именно в тот момент*/
    private var job: Job? = null

    /** тут далой условие вопервых слишком по уебански выглядить во вторых так не будет работать
     * потому что ты меняешь не состояния потока а сам поток
     * поэтомы оставляем только так, а вся магия по выбору откуда грузить ложится на плечи медиатора*/
    @OptIn(ExperimentalCoroutinesApi::class)
    fun getPhoto() = query.asStateFlow()
        .flatMapLatest { photosPagingUseCase.getPhoto(Requester.ALL_LIST.apply { query =it }) }
        .cachedIn(viewModelScope)


    fun like(item: Photo) {
        viewModelScope.launch(Dispatchers.IO + handler) {
            photoLikeUseCase.likePhoto(item)
            _loadState.value = LoadState.SUCCESS
        }
    }

    fun setQuery(newText: String, refresh: () -> Unit) {
        job?.cancel()
        job = viewModelScope.launch() {
            delay(500)
            query.value = newText
            refresh()
        }
    }
}