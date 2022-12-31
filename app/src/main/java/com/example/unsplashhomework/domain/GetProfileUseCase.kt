package com.example.unsplashhomework.domain

import javax.inject.Inject

class GetProfileUseCase @Inject constructor(private val profileRemoteRepository: ProfileRemoteRepository) {

    suspend fun getProfile() = profileRemoteRepository.getProfile()
}