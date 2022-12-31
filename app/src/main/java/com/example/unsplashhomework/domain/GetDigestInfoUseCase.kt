package com.example.unsplashhomework.domain

import javax.inject.Inject

class GetDigestInfoUseCase @Inject constructor(
    private val digestRemoteRepository: DigestRemoteRepository
    ) {

    suspend fun getDigestInfo(id: String) = digestRemoteRepository.getDigestInfo(id)
}