package com.example.unsplashhomework.domain.repository

import com.example.unsplashhomework.domain.model.Digest

interface DigestRemoteRepository {

    suspend fun getDigests(page: Int): List<Digest>

    suspend fun getDigestInfo(id: String): Digest
}