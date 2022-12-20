package com.example.unsplashhomework.presentation.photos

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.unsplashhomework.data.api.ApiPhotos
import com.example.unsplashhomework.data.api.ApiToken
import com.example.unsplashhomework.data.api.AuthTokenProvider
import com.example.unsplashhomework.data.api.ResponseToken
import com.example.unsplashhomework.data.remote.PhotosModel
import com.example.unsplashhomework.data.remote.PhotosPagingUseCase
import com.example.unsplashhomework.data.remote.RemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val apiToken: ApiToken,
    private val photosPagingUseCase: PhotosPagingUseCase,
    val apiPhotos: ApiPhotos,
    val repository: RemoteRepository
) : ViewModel() {

    val a = MutableSharedFlow<ResponseToken>()


    val photo: Flow<PagingData<PhotosModel.PhotosModelItem>> =
        photosPagingUseCase.execute().cachedIn(viewModelScope)


    fun createToken(code: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.e("Kart", "start")
                val b = apiToken.getToken(code = code)
                Log.e("Kart", "${b.access_token}")
                a.emit(b)
                AuthTokenProvider.authToken = b.access_token //вот тут прокидываю токен в интерсептор
//                repository.getData(1)   //это для запуска гета в обход адаптера

            } catch (e: Exception) {
                Log.e("Kart", e.message.toString())
            }
        }
    }
}