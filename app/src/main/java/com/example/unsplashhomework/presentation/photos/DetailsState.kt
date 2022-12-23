package com.example.unsplashhomework.presentation.photos

import com.example.unsplashhomework.data.remote.photodetailsmodel.PhotoDetails

sealed class DetailsState {
    data class Success(val data: PhotoDetails): DetailsState()
    object LoadingError: DetailsState()
    object NotStartedYet : DetailsState()
}
