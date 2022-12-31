package com.example.unsplashhomework.presentation.user

import androidx.lifecycle.viewModelScope
import com.example.unsplashhomework.data.state.LoadState
import com.example.unsplashhomework.data.state.Requester
import com.example.unsplashhomework.domain.GetProfileUseCase
import com.example.unsplashhomework.domain.PhotoLikeUseCase
import com.example.unsplashhomework.domain.PhotosPagingUseCase
import com.example.unsplashhomework.domain.model.Photo
import com.example.unsplashhomework.tools.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val photosPagingUseCase: PhotosPagingUseCase,
    private val photoLikeUseCase: PhotoLikeUseCase
) : BaseViewModel() {

    suspend fun getProfile()  = getProfileUseCase.getProfile()

    fun getPhoto(userName: String) =
        photosPagingUseCase.getPhoto(Requester.PROFILE.apply { param = userName })

    fun like(item: Photo) {
        viewModelScope.launch(Dispatchers.IO + handler) {
            photoLikeUseCase.likePhoto(item)
            _loadState.value = LoadState.SUCCESS
        }
    }
}