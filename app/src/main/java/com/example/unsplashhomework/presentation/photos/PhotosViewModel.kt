package com.example.unsplashhomework.presentation.photos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.unsplashhomework.data.api.ApiPhotos
import com.example.unsplashhomework.data.remote.PhotosPagingUseCase
import com.example.unsplashhomework.data.remote.photosmodel.PhotosModelItem
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
    /**это для тестов**/
    private val apiPhotos: ApiPhotos
) : ViewModel() {

    val a = MutableSharedFlow<PagingData<PhotosModelItem>>()

//    fun createToken(code: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            AuthTokenProvider.authToken = apiToken.getToken(code = code).access_token
//            getPhoto()
//        }
//    }

    suspend fun getPhoto() {
            photosPagingUseCase.getPhoto().flow.onEach {
                a.emit(it)
            }.launchIn(viewModelScope)
    }

    /**и это для тестов**/
    fun like(id: String, isLiked: Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            if (isLiked) apiPhotos.unlike(id)
            else apiPhotos.like(id)
        }
    }
}