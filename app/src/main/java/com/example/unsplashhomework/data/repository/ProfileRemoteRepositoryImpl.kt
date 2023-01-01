package com.example.unsplashhomework.data.repository

import com.example.unsplashhomework.data.api.ApiProfile
import com.example.unsplashhomework.domain.repository.ProfileRemoteRepository
import com.example.unsplashhomework.domain.model.Profile
import javax.inject.Inject

class ProfileRemoteRepositoryImpl @Inject constructor(private val apiProfile: ApiProfile):
    ProfileRemoteRepository {

    override suspend fun getProfile(): Profile = apiProfile.getProfile().toProfile()
}