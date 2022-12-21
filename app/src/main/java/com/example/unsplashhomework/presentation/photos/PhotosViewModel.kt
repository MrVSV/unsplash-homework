package com.example.unsplashhomework.presentation.photos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.unsplashhomework.data.api.ApiLikes
import com.example.unsplashhomework.data.api.ApiToken
import com.example.unsplashhomework.data.api.AuthTokenProvider
import com.example.unsplashhomework.data.remote.PhotosModel
import com.example.unsplashhomework.data.remote.PhotosPagingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val apiToken: ApiToken,
    private val photosPagingUseCase: PhotosPagingUseCase,
    /**это для тестов**/
    val apiLikes: ApiLikes
) : ViewModel() {

    val a = MutableSharedFlow<PagingData<PhotosModel.PhotosModelItem>>()

    fun createToken(code: String) {
        viewModelScope.launch(Dispatchers.IO) {
            AuthTokenProvider.authToken = apiToken.getToken(code = code).access_token
            getPhoto()
        }
    }

    private suspend fun getPhoto() {
            photosPagingUseCase.getPhoto().flow.onEach {
                a.emit(it)
            }.launchIn(viewModelScope)
    }
    /**и это для тестов**/
    suspend fun like(id: String) = apiLikes.like(id)
    suspend fun unlike(id: String) = apiLikes.unlike(id)
}