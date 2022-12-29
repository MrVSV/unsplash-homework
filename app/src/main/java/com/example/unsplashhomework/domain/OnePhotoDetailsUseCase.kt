package com.example.unsplashhomework.domain

import com.example.unsplashhomework.domain.model.PhotoDetails

interface OnePhotoDetailsUseCase {

    suspend fun getPhotoDetails(id: String): PhotoDetails

}