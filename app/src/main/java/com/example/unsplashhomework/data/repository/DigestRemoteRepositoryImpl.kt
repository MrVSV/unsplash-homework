package com.example.unsplashhomework.data.repository

import com.example.unsplashhomework.data.api.ApiDigest
import com.example.unsplashhomework.data.model.Digest
import com.example.unsplashhomework.domain.DigestRemoteRepository
import com.example.unsplashhomework.tools.toListDigest
import javax.inject.Inject

class DigestRemoteRepositoryImpl @Inject constructor(private val apiDigest: ApiDigest): DigestRemoteRepository {
    override suspend fun getDigests(page: Int): List<Digest> = apiDigest.getDigests(page).toListDigest()
}