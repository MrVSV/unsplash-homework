package com.example.unsplashhomework.presentation.user

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.unsplashhomework.data.state.LoadState
import com.example.unsplashhomework.data.state.Requester
import com.example.unsplashhomework.domain.model.Photo
import com.example.unsplashhomework.domain.model.Profile
import com.example.unsplashhomework.domain.usecase.GetProfileUseCase
import com.example.unsplashhomework.domain.usecase.PhotoLikeUseCase
import com.example.unsplashhomework.domain.usecase.PhotosPagingUseCase
import com.example.unsplashhomework.tools.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val photosPagingUseCase: PhotosPagingUseCase,
    private val photoLikeUseCase: PhotoLikeUseCase
) : BaseViewModel() {

    private val userName = MutableStateFlow("")
    private var job: Job? = null

    private val _profile = MutableSharedFlow<Profile>()
    val profile = _profile.asSharedFlow()

    private val _state = MutableStateFlow<ProfileState>(ProfileState.NotStartedYet)
    val state = _state.asStateFlow()

    fun getProfile() {
        viewModelScope.launch(Dispatchers.IO + handler) {
            val a = getProfileUseCase.getProfile()
            _loadState.value = LoadState.SUCCESS
            _state.value = ProfileState.Success(a)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getPhoto() = userName.asStateFlow()
        .flatMapLatest { photosPagingUseCase.getPhoto(Requester.PROFILE.apply { param = it })}
        .cachedIn(CoroutineScope(Dispatchers.IO))

    fun like(item: Photo) {
        viewModelScope.launch(Dispatchers.IO + handler) {
            photoLikeUseCase.likePhoto(item)
            _loadState.value = LoadState.SUCCESS
        }
    }

    fun setUsername(newText: String, refresh: () -> Unit) {
        job?.cancel()
        job = viewModelScope.launch {
            userName.value = newText
            refresh()
        }
    }
}