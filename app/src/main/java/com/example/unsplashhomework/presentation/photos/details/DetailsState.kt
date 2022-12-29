package com.example.unsplashhomework.presentation.photos.details

import com.example.unsplashhomework.domain.model.PhotoDetails

sealed class DetailsState {
    data class Success(val data: PhotoDetails): DetailsState()
    object LoadingError: DetailsState()
    object NotStartedYet : DetailsState()
}
