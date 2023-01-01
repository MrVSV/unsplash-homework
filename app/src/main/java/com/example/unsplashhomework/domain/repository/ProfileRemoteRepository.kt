package com.example.unsplashhomework.domain.repository

import com.example.unsplashhomework.domain.model.Profile

interface ProfileRemoteRepository {

    suspend fun getProfile(): Profile
}