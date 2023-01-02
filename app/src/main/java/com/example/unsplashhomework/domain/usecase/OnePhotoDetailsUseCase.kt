package com.example.unsplashhomework.domain.usecase

import com.example.unsplashhomework.domain.model.PhotoDetails

interface OnePhotoDetailsUseCase {

    suspend fun getPhotoDetails(id: String): PhotoDetails

}