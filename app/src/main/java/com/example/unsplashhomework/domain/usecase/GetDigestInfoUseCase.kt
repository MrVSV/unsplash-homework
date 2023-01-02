package com.example.unsplashhomework.domain.usecase

import com.example.unsplashhomework.domain.model.Digest

interface GetDigestInfoUseCase {

    suspend fun getDigestInfo(id: String): Digest
}