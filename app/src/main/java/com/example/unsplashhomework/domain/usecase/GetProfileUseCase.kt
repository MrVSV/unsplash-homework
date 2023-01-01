package com.example.unsplashhomework.domain.usecase

import com.example.unsplashhomework.domain.model.Profile

interface GetProfileUseCase {

    suspend fun getProfile(): Profile
}