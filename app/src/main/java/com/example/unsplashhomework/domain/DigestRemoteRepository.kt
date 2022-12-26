package com.example.unsplashhomework.domain

import com.example.unsplashhomework.data.model.Digest

interface DigestRemoteRepository {

 suspend fun getDigests(page: Int) : List<Digest>
}