package com.example.unsplashhomework.presentation.photos.details

import com.example.unsplashhomework.data.api.photodto.PhotoDetailsDto

sealed class DetailsState {
    data class Success(val data: PhotoDetailsDto): DetailsState()
    object LoadingError: DetailsState()
    object NotStartedYet : DetailsState()
}
