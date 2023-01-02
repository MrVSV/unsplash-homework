package com.example.unsplashhomework.presentation.collections

import androidx.lifecycle.viewModelScope
import com.example.unsplashhomework.data.state.LoadState
import com.example.unsplashhomework.data.state.Requester
import com.example.unsplashhomework.domain.model.Photo
import com.example.unsplashhomework.domain.usecase.GetDigestInfoUseCase
import com.example.unsplashhomework.domain.usecase.PhotoLikeUseCase
import com.example.unsplashhomework.domain.usecase.PhotosPagingUseCase
import com.example.unsplashhomework.tools.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DigestDetailsViewModel @Inject constructor(
    private val photosPagingUseCase: PhotosPagingUseCase,
    private val photoLikeUseCase: PhotoLikeUseCase,
    private val getDigestInfoUseCase: GetDigestInfoUseCase
) : BaseViewModel() {

    suspend fun getDigestInfo(id: String)  = getDigestInfoUseCase.getDigestInfo(id)

    fun getPhoto(id: String) =
        photosPagingUseCase.getPhoto(Requester.COLLECTIONS.apply { param = id })

    fun like(item: Photo) {
        viewModelScope.launch(Dispatchers.IO + handler) {
            photoLikeUseCase.likePhoto(item)
            _loadState.value = LoadState.SUCCESS
        }
    }
}
