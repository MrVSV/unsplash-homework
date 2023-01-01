package com.example.unsplashhomework.data.usecase

import com.example.unsplashhomework.domain.repository.DigestRemoteRepository
import com.example.unsplashhomework.domain.usecase.GetDigestInfoUseCase
import javax.inject.Inject

class GetDigestInfoUseCaseImpl @Inject constructor(
    private val digestRemoteRepository: DigestRemoteRepository
    ): GetDigestInfoUseCase {

    override suspend fun getDigestInfo(id: String) = digestRemoteRepository.getDigestInfo(id)
}