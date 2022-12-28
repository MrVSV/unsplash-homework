package com.example.unsplashhomework.presentation.photos.details

import com.example.unsplashhomework.data.model.PhotoDetails

sealed class DetailsState {
    data class Success(val data: PhotoDetails): DetailsState()
    object LoadingError: DetailsState()
    object NotStartedYet : DetailsState()
}
