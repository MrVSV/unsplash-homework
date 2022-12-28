package com.example.unsplashhomework.domain

import com.example.unsplashhomework.data.model.PhotoDetails

interface OnePhotoDetailsUseCase {

    suspend fun getPhotoDetails(id: String): PhotoDetails

}