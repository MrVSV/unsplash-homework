package com.example.unsplashhomework.data.usecase

import com.example.unsplashhomework.domain.repository.ProfileRemoteRepository
import com.example.unsplashhomework.domain.usecase.GetProfileUseCase
import javax.inject.Inject

class GetProfileUseCaseImpl @Inject constructor(
    private val profileRemoteRepository: ProfileRemoteRepository
    ) : GetProfileUseCase {

    override suspend fun getProfile() = profileRemoteRepository.getProfile()
}