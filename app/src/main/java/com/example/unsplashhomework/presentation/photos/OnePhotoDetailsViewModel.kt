package com.example.unsplashhomework.presentation.photos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unsplashhomework.data.remote.RemoteRepository
import com.example.unsplashhomework.data.remote.photodetailsmodel.PhotoDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnePhotoDetailsViewModel @Inject constructor(private val repository: RemoteRepository) :
    ViewModel() {

    private val _state = MutableStateFlow<DetailsState>(DetailsState.NotStartedYet)
    val state = _state.asStateFlow()

    fun loadPhotoDetails(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _state.value = DetailsState.Success(repository.getPhotoDetails(id = id))
            } catch (_: Exception) {
                _state.value = DetailsState.LoadingError
            }
        }
    }
}